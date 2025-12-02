package com.vibely.service.impl;

import com.vibely.entity.*;
import com.vibely.repository.ProfileRepository;
import com.vibely.service.MatchEventService;
import com.vibely.service.RecommendationService;
import com.vibely.service.PersonalityScoreService;
import com.vibely.service.PreferenceService;
import com.vibely.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendationServiceImpl implements RecommendationService {

    private final ProfileRepository profileRepository;
    private final PreferenceService preferenceService;
    private final PersonalityScoreService personalityScoreService;
    private final MatchEventService matchEventService;
    private final ProfileService profileService;

    // weights (tweakable)
    private static final double W_DISTANCE = 0.25;
    private static final double W_BUDGET = 0.25;
    private static final double W_PERSONALITY = 0.20;
    private static final double W_PETS = 0.12;
    private static final double W_CLEANLINESS = 0.10;
    private static final double W_LIFESTYLE = 0.08;

    @Override
    public List<MatchScore> recommend(User user, double maxDistanceKm, int limit) {
        // Fetch current user's profile
        Optional<Profile> meOpt = profileService.findByUserId(user.getId());
        if (meOpt.isEmpty()) return Collections.emptyList();
        Profile me = meOpt.get();

        // Load all profiles and filter out self
        List<Profile> all = profileRepository.findAll().stream()
                .filter(p -> !p.getUserId().equals(user.getId()))
                .collect(Collectors.toList());

        // Optionally exclude already rejected/liked targets (here we just compute everything)
        List<MatchScore> scored = new ArrayList<>();

        // Load personality for me (optional)
        Optional<PersonalityScore> myPersonality = personalityScoreService.findByUserId(user.getId());

        for (Profile candidate : all) {
            // Hard filters
            if (!budgetOverlap(me, candidate)) continue;
            double distanceKm = haversineKm(me.getLocationLat(), me.getLocationLng(),
                    candidate.getLocationLat(), candidate.getLocationLng());
            if (distanceKm > maxDistanceKm) continue;
            if (!moveInDateCompatible(me, candidate)) continue;

            // Soft scores
            double distanceScore = Math.max(0.0, 1.0 - distanceKm / Math.max(maxDistanceKm, 1.0));
            double budgetScore = computeBudgetScore(me.getBudgetMin(), me.getBudgetMax(), candidate.getBudgetMin(), candidate.getBudgetMax());
            double personalityScore = computePersonality(me.getUserId(), candidate.getUserId(), myPersonality);
            double petsScore = computePetsScore(me.getPetsPreference(), candidate.getPetsPreference());
            double cleanlinessScore = 1.0 - Math.abs(nullSafe(me.getCleanlinessLevel()) - nullSafe(candidate.getCleanlinessLevel())) / 4.0;
            double lifestyleScore = computeLifestyleScore(me, candidate);

            double finalScore = W_DISTANCE * distanceScore
                              + W_BUDGET * budgetScore
                              + W_PERSONALITY * personalityScore
                              + W_PETS * petsScore
                              + W_CLEANLINESS * cleanlinessScore
                              + W_LIFESTYLE * lifestyleScore;

            List<String> reasons = topReasons(Map.of(
                    "distance", distanceScore,
                    "budget", budgetScore,
                    "personality", personalityScore,
                    "pets", petsScore,
                    "cleanliness", cleanlinessScore,
                    "lifestyle", lifestyleScore
            ));

            scored.add(new MatchScore(candidate, finalScore, reasons, candidate.getId()));
        }

        return scored.stream()
                .sorted(Comparator.comparingDouble(MatchScore::score).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private double nullSafe(Integer v) {
        return v == null ? 3.0 : v.doubleValue(); // assume neutral cleanliness
    }

    private boolean budgetOverlap(Profile a, Profile b) {
        if (a.getBudgetMin() == null || a.getBudgetMax() == null || b.getBudgetMin() == null || b.getBudgetMax() == null) {
            return true; // be permissive if budgets missing
        }
        return a.getBudgetMin() <= b.getBudgetMax() && b.getBudgetMin() <= a.getBudgetMax();
    }

    private boolean moveInDateCompatible(Profile a, Profile b) {
        if (a.getMoveInDate() == null || b.getMoveInDate() == null) return true;
        long days = Math.abs(a.getMoveInDate().toEpochDay() - b.getMoveInDate().toEpochDay());
        return days <= 60; // threshold
    }

    private double computeBudgetScore(Integer aMin, Integer aMax, Integer bMin, Integer bMax) {
        if (aMin == null || aMax == null || bMin == null || bMax == null) return 0.5;
        int overlap = Math.min(aMax, bMax) - Math.max(aMin, bMin);
        if (overlap <= 0) return 0.0;
        int totalSpan = Math.max(aMax, bMax) - Math.min(aMin, bMin);
        return Math.min(1.0, (double) overlap / Math.max(totalSpan, 1));
    }

    private double computePersonality(UUID userA, UUID userB, Optional<PersonalityScore> myPersonality) {
        Optional<PersonalityScore> other = personalityScoreService.findByUserId(userB);
        if (myPersonality.isEmpty() || other.isEmpty()) {
            // fallback: if missing, give neutral score
            return 0.5;
        }
        int s1 = myPersonality.get().getRawScore();
        int s2 = other.get().getRawScore();
        return 1.0 - (Math.abs(s1 - s2) / 100.0);
    }

    private double computePetsScore(PetsPreference p1, PetsPreference p2) {
        if (p1 == null || p2 == null) return 0.7;
        if (p1 == p2) return 1.0;
        if (p1 == PetsPreference.OK_WITH_PETS || p2 == PetsPreference.OK_WITH_PETS) return 0.7;
        if ((p1 == PetsPreference.HAS_PETS && p2 == PetsPreference.NO_PETS) ||
            (p2 == PetsPreference.HAS_PETS && p1 == PetsPreference.NO_PETS)) return 0.0;
        return 0.5;
    }

    private double computeLifestyleScore(Profile a, Profile b) {
        double s = 0.0;
        if (Objects.equals(a.getSmoking(), b.getSmoking())) s += 1.0;
        s += sleepMatchScore(a.getSleepSchedule(), b.getSleepSchedule());
        return s / 2.0;
    }

    private double sleepMatchScore(SleepSchedule s1, SleepSchedule s2) {
        if (s1 == null || s2 == null) return 0.5;
        if (s1 == s2) return 1.0;
        if ((s1 == SleepSchedule.EARLY && s2 == SleepSchedule.LATE) ||
            (s1 == SleepSchedule.LATE && s2 == SleepSchedule.EARLY)) return 0.0;
        return 0.5;
    }

    private List<String> topReasons(Map<String, Double> components) {
        return components.entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry<String, Double>::getValue).reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .map(this::reasonLabel)
                .collect(Collectors.toList());
    }

    private String reasonLabel(String key) {
        return switch (key) {
            case "distance" -> "Nearby";
            case "budget" -> "Budget fits";
            case "personality" -> "Similar personality";
            case "pets" -> "Pets compatible";
            case "cleanliness" -> "Similar cleanliness";
            case "lifestyle" -> "Lifestyle match";
            default -> key;
        };
    }

    // Haversine
    private double haversineKm(Double lat1, Double lon1, Double lat2, Double lon2) {
        if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) return Double.MAX_VALUE;
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}
