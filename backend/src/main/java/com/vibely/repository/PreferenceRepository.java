package com.vibely.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vibely.entity.Preference;
import com.vibely.entity.User;

public interface PreferenceRepository extends JpaRepository<Preference, UUID> {
    Optional<Preference> findByUser(User user);
    Optional<Preference> findByUser_Id(UUID userId);
}
