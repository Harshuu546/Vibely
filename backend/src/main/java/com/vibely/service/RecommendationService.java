package com.vibely.service;

import java.util.List;
import java.util.UUID;

import com.vibely.entity.Profile;
import com.vibely.entity.User;

public interface RecommendationService {
    /**
     * Recommend candidate profiles for the given user.
     *
     * @param user the requesting user entity
     * @param maxDistanceKm maximum distance to consider
     * @param limit maximum number of recommendations
     * @return list of MatchScore results (profile + score + reasons)
     */
    List<RecommendationService.MatchScore> recommend(User user, double maxDistanceKm, int limit);

    record MatchScore(Profile profile, double score, List<String> reasons, UUID profileId) { }
}
