package com.vibely.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vibely.entity.MatchAction;
import com.vibely.entity.MatchEvent;
import com.vibely.entity.User;
import com.vibely.service.MatchEventService;
import com.vibely.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchEventService matchEventService;
    private final UserService userService;

    @PostMapping("/{userId}/like/{targetId}")
    public ResponseEntity<?> like(@PathVariable UUID userId, @PathVariable UUID targetId) {
        return doAction(userId, targetId, MatchAction.LIKE);
    }

    @PostMapping("/{userId}/reject/{targetId}")
    public ResponseEntity<?> reject(@PathVariable UUID userId, @PathVariable UUID targetId) {
        return doAction(userId, targetId, MatchAction.REJECT);
    }

    private ResponseEntity<?> doAction(UUID userId, UUID targetId, MatchAction action) {
        Optional<User> u = userService.findById(userId);
        Optional<User> t = userService.findById(targetId);
        if (u.isEmpty() || t.isEmpty()) {
            return ResponseEntity.badRequest().body("user or target not found");
        }

        MatchEvent event = MatchEvent.builder()
                .id(UUID.randomUUID())
                .user(u.get())
                .targetUser(t.get())
                .action(action)
                .createdAt(OffsetDateTime.now())
                .build();

        MatchEvent saved = matchEventService.save(event);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{userId}/events")
    public ResponseEntity<?> events(@PathVariable UUID userId) {
        Optional<User> u = userService.findById(userId);
        if (u.isEmpty()) return ResponseEntity.badRequest().body("user not found");
        List<MatchEvent> ev = matchEventService.findByUserId(userId);
        return ResponseEntity.ok(ev);
    }
}
