package com.vibely.dto.match;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchEventRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID targetUserId;
}
