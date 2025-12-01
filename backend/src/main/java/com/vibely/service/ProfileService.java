package com.vibely.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vibely.entity.Profile;

public interface ProfileService {
    Profile save(Profile profile);
    Optional<Profile> findById(UUID id);
    Optional<Profile> findByUserId(UUID userId);
    List<Profile> findAll();
    void deleteById(UUID id);
}
