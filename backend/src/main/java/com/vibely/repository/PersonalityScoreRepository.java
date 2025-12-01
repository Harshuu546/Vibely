package com.vibely.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vibely.entity.PersonalityScore;
import com.vibely.entity.User;

public interface PersonalityScoreRepository extends JpaRepository<PersonalityScore, UUID> {
    Optional<PersonalityScore> findByUser(User user);
    Optional<PersonalityScore> findByUser_Id(UUID userId);
}
