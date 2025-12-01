package com.vibely.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibely.entity.MatchAction;
import com.vibely.entity.MatchEvent;
import com.vibely.entity.User;
import com.vibely.repository.MatchEventRepository;
import com.vibely.service.MatchEventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchEventServiceImpl implements MatchEventService {

    private final MatchEventRepository repository;

    @Override
    public MatchEvent save(MatchEvent event) {
        return repository.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchEvent> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchEvent> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchEvent> findByUserId(UUID userId) {
        return repository.findByUser_Id(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchEvent> findByTargetUserId(UUID targetUserId) {
        return repository.findByTargetUser_Id(targetUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatchEvent> findLatestByUserAndTarget(UUID userId, UUID targetUserId) {
        return repository.findFirstByUser_IdAndTargetUser_IdOrderByCreatedAtDesc(userId, targetUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByUserAndAction(UUID userId, MatchAction action) {
        return repository.findByUser_Id(userId).stream()
                .filter(e -> e.getAction() == action)
                .count();
    }
}
