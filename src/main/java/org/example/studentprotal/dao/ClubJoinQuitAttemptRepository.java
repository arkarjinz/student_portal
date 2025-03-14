package org.example.studentprotal.dao;

import org.example.studentprotal.entity.ClubJoinQuitAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubJoinQuitAttemptRepository extends JpaRepository<ClubJoinQuitAttempt, Long> {
    Optional<ClubJoinQuitAttempt> findByClubNameAndUsername(String clubName, String username);
}
