package com.vibely.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "profiles")
public class Profile {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;


    // Link to user (one-to-one). Unique constraint ensures one profile per user.
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true, columnDefinition = "uuid")
    private User user;

    @Column(name = "bio", columnDefinition = "text")
    private String bio;

    @Column(name = "gender", length = 30)
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_lng")
    private Double locationLng;

    @Column(name = "current_city", length = 120)
    private String currentCity;

    @Column(name = "budget_min")
    private Integer budgetMin;

    @Column(name = "budget_max")
    private Integer budgetMax;

    @Column(name = "has_pets")
    private Boolean hasPets;

    @Enumerated(EnumType.STRING)
    @Column(name = "pets_preference", length = 30)
    private PetsPreference petsPreference;

    @Column(name = "cleanliness_level")
    private Integer cleanlinessLevel; // 1..5

    @Column(name = "smoking")
    private Boolean smoking;

    @Enumerated(EnumType.STRING)
    @Column(name = "sleep_schedule", length = 20)
    private SleepSchedule sleepSchedule;

    @Column(name = "move_in_date")
    private LocalDate moveInDate;

    @Column(name = "profile_completion_percent")
    private Integer profileCompletionPercent;

    @UpdateTimestamp
    @Column(name = "last_active_at", columnDefinition = "timestamp with time zone")
    private OffsetDateTime lastActiveAt;

    // inside class Profile
    public UUID getUserId() {
        return user != null ? user.getId() : null;
    }
}
