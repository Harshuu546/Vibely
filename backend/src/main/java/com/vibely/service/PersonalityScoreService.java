package com.vibely.service;

import java.util.Optional;
import java.util.UUID;

import com.vibely.entity.PersonalityScore;

public interface PersonalityScoreService {
    PersonalityScore save(PersonalityScore score);
    Optional<PersonalityScore> findById(UUID id);
    Optional<PersonalityScore> findByUserId(UUID userId);
    void deleteById(UUID id);
}
