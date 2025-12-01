package com.vibely.dto.profile;

import com.vibely.entity.PetsPreference;
import com.vibely.entity.SleepSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private UUID id;
    private UUID userId;
    private String bio;
    private String gender;
    private Integer age;
    private Double locationLat;
    private Double locationLng;
    private String currentCity;
    private Integer budgetMin;
    private Integer budgetMax;
    private Boolean hasPets;
    private PetsPreference petsPreference;
    private Integer cleanlinessLevel;
    private Boolean smoking;
    private SleepSchedule sleepSchedule;
    private LocalDate moveInDate;
    private Integer profileCompletionPercent;
    private OffsetDateTime lastActiveAt;
}
