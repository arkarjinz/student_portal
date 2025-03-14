package org.example.studentprotal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "club_join_quit_attempts")
public class ClubJoinQuitAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clubName;
    private String username;

    // Count of join/quit events
    private int attemptCount;

    // If banned, the time until which the ban is effective
    private LocalDateTime bannedUntil;
}
