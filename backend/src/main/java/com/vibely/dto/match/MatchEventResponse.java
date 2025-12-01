package com.vibely.dto.match;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.vibely.entity.MatchAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchEventResponse {
    private UUID id;
    private UUID userId;
    private UUID targetUserId;
    private MatchAction action;
    private OffsetDateTime createdAt;
}
