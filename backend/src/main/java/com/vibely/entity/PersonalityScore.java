package com.vibely.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "personality_scores")
public class PersonalityScore {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true, columnDefinition = "uuid")
    private User user;

    @Column(name = "raw_score")
    private Integer rawScore; // 0..100

    @Enumerated(EnumType.STRING)
    @Column(name = "personality_type", length = 30)
    private PersonalityType type;

    // Store quiz responses as JSON text (Postgres jsonb is preferred; using text to avoid extra dependency)
    @Column(name = "quiz_responses_json", columnDefinition = "text")
    private String quizResponsesJson;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp with time zone", updatable = false)
    private OffsetDateTime createdAt;
}
