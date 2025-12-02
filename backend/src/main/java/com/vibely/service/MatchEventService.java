package com.vibely.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vibely.entity.MatchAction;
import com.vibely.entity.MatchEvent;
import com.vibely.entity.User;

public interface MatchEventService {
    MatchEvent save(MatchEvent event);
    Optional<MatchEvent> findById(UUID id);
    List<MatchEvent> findByUser(User user);
    List<MatchEvent> findByUserId(UUID userId);
    List<MatchEvent> findByTargetUserId(UUID targetUserId);
    Optional<MatchEvent> findLatestByUserAndTarget(UUID userId, UUID targetUserId);
    long countByUserAndAction(UUID userId, MatchAction action);
}
