package com.vibely.service.impl;

import com.vibely.entity.Preference;
import com.vibely.repository.PreferenceRepository;
import com.vibely.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceRepository preferenceRepository;

    @Override
    public Preference save(Preference preference) {
        return preferenceRepository.save(preference);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Preference> findById(UUID id) {
        return preferenceRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Preference> findByUserId(UUID userId) {
        return preferenceRepository.findByUser_Id(userId);
    }

    @Override
    public void deleteById(UUID id) {
        preferenceRepository.deleteById(id);
    }
}
