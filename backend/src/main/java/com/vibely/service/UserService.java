package com.vibely.service;

import java.util.Optional;
import java.util.UUID;

import com.vibely.entity.User;

public interface UserService {
    User create(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User update(User user);
    void delete(UUID id);
}
