package com.vibely.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vibely.entity.Profile;
import com.vibely.entity.User;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUser(User user);
    Optional<Profile> findByUser_Id(UUID userId);
}
