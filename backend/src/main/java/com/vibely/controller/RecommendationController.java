package com.vibely.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vibely.entity.User;
import com.vibely.service.RecommendationService;
import com.vibely.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final UserService userService;

    /**
     * GET /api/v1/recommendations/{userId}?maxDistanceKm=20&limit=10
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> recommend(
            @PathVariable("userId") UUID userId,
            @RequestParam(name = "maxDistanceKm", defaultValue = "25") double maxDistanceKm,
            @RequestParam(name = "limit", defaultValue = "20") int limit) {

        Optional<User> u = userService.findById(userId);
        if (u.isEmpty()) return ResponseEntity.badRequest().body("user not found");

        List<RecommendationService.MatchScore> list = recommendationService.recommend(u.get(), maxDistanceKm, limit);
        return ResponseEntity.ok(list);
    }
}
