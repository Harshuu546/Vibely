package com.vibely.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vibely.entity.MatchAction;
import com.vibely.entity.MatchEvent;
import com.vibely.entity.User;

public interface MatchEventRepository extends JpaRepository<MatchEvent, UUID> {

    List<MatchEvent> findByUser(User user);
    List<MatchEvent> findByUser_Id(UUID userId);

    List<MatchEvent> findByTargetUser(User targetUser);
    List<MatchEvent> findByTargetUser_Id(UUID targetUserId);

    List<MatchEvent> findByUserAndTargetUser(User user, User targetUser);
    List<MatchEvent> findByUser_IdAndTargetUser_Id(UUID userId, UUID targetUserId);

    List<MatchEvent> findByAction(MatchAction action);

    Optional<MatchEvent> findFirstByUser_IdAndTargetUser_IdOrderByCreatedAtDesc(UUID userId, UUID targetUserId);
}
