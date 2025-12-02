package com.vibely.dto.profile;

import java.time.LocalDate;

import com.vibely.entity.PetsPreference;
import com.vibely.entity.SleepSchedule;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {

    // keep userId nullable if you will set user from auth principal
    public String bio;
    public String gender;
    public Integer age;

    public Double locationLat;
    public Double locationLng;
    public String currentCity;

    public Integer budgetMin;
    public Integer budgetMax;

    public Boolean hasPets;
    public PetsPreference petsPreference;

    @Min(1) @Max(5)
    public Integer cleanlinessLevel;

    public Boolean smoking;
    public SleepSchedule sleepSchedule;

    public LocalDate moveInDate;
}
