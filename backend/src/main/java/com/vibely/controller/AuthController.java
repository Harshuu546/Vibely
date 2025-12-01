package com.vibely.controller;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vibely.entity.User;
import com.vibely.entity.UserRole;
import com.vibely.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (req.email == null || req.password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "email and password are required"));
        }

        if (userService.existsByEmail(req.email)) {
            return ResponseEntity.badRequest().body(Map.of("error", "email already registered"));
        }

        User u = User.builder()
                .id(UUID.randomUUID())
                .email(req.email)
                // TODO: replace with hashed password using PasswordEncoder
                .passwordHash(req.password)
                .firstName(req.firstName)
                .lastName(req.lastName)
                .role(UserRole.USER)
                .enabled(true)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        User created = userService.create(u);
        // Hide passwordHash in response
        created.setPasswordHash(null);
        return ResponseEntity.ok(Map.of("user", created));
    }

    /**
     * Stub login endpoint. Replace with JWT authentication later.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<User> opt = userService.findByEmail(req.email);
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "invalid credentials"));
        }

        User user = opt.get();
        // NOTE: currently comparing plaintext; replace with PasswordEncoder.matches(...) after wiring auth
        if (!user.getPasswordHash().equals(req.password)) {
            return ResponseEntity.status(401).body(Map.of("error", "invalid credentials"));
        }

        // TODO: generate JWT token here
        return ResponseEntity.ok(Map.of("message", "login successful", "userId", user.getId()));
    }

    /* --------------------
       DTOs (inner classes)
       Extract into dto/ if you prefer
       -------------------- */
    public static class RegisterRequest {
        public String email;
        public String password;
        public String firstName;
        public String lastName;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }
}
