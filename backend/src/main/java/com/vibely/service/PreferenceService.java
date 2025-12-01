package com.vibely.service;

import java.util.Optional;
import java.util.UUID;

import com.vibely.entity.Preference;

public interface PreferenceService {
    Preference save(Preference preference);
    Optional<Preference> findById(UUID id);
    Optional<Preference> findByUserId(UUID userId);
    void deleteById(UUID id);
}
