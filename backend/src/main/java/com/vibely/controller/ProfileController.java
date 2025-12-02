package com.vibely.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vibely.entity.Profile;
import com.vibely.entity.User;
import com.vibely.service.ProfileService;
import com.vibely.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    /**
     * Create or update a profile for a given userId.
     * In a real app, userId should come from authenticated principal.
     */
    @PostMapping("/{userId}")
    public ResponseEntity<?> upsertProfile(@PathVariable("userId") UUID userId, @RequestBody Profile incoming) {
        Optional<User> u = userService.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.badRequest().body("user not found");
        }

        // Ensure profile.user is set correctly (avoid caller messing user field)
        incoming.setUser(u.get());
        Profile saved = profileService.save(incoming);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getProfileByUser(@PathVariable("userId") UUID userId) {
        Optional<Profile> p = profileService.findByUserId(userId);
        return p.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable("id") UUID id) {
        Optional<Profile> p = profileService.findById(id);
        return p.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
