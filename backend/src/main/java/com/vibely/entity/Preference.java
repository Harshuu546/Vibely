package com.vibely.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "preferences")
public class Preference {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true, columnDefinition = "uuid")
    private User user;

    @Column(name = "preferred_location_lat")
    private Double preferredLocationLat;

    @Column(name = "preferred_location_lng")
    private Double preferredLocationLng;

    @Column(name = "preferred_radius_km")
    private Integer preferredRadiusKm;

    @Column(name = "budget_min")
    private Integer budgetMin;

    @Column(name = "budget_max")
    private Integer budgetMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "pets_preference", length = 30)
    private PetsPreference petsPreference;

    @Column(name = "cleanliness_preference")
    private Integer cleanlinessPreference;

    @Enumerated(EnumType.STRING)
    @Column(name = "sleep_schedule_preference", length = 20)
    private SleepSchedule sleepSchedulePreference;
}
