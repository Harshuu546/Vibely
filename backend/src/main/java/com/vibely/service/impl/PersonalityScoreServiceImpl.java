package com.vibely.service.impl;

import com.vibely.entity.PersonalityScore;
import com.vibely.repository.PersonalityScoreRepository;
import com.vibely.service.PersonalityScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonalityScoreServiceImpl implements PersonalityScoreService {

    private final PersonalityScoreRepository repository;

    @Override
    public PersonalityScore save(PersonalityScore score) {
        return repository.save(score);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalityScore> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalityScore> findByUserId(UUID userId) {
        return repository.findByUser_Id(userId);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
