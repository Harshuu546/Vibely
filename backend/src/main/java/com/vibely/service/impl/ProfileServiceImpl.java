package com.vibely.service.impl;

import com.vibely.entity.Profile;
import com.vibely.repository.ProfileRepository;
import com.vibely.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> findById(UUID id) {
        return profileRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> findByUserId(UUID userId) {
        return profileRepository.findByUser_Id(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        profileRepository.deleteById(id);
    }
}
