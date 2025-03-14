package org.example.studentprotal.service;

import lombok.RequiredArgsConstructor;
import org.example.studentprotal.dao.ClubJoinQuitAttemptRepository;
import org.example.studentprotal.entity.ClubJoinQuitAttempt;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubBanService {

    private final ClubJoinQuitAttemptRepository repository;
    private static final int MAX_ATTEMPTS = 3;
    private static final int BAN_DURATION_MINUTES = 30;

    // Check if the user is currently banned for the given club
    public boolean isBanned(String username, String clubName) {
        Optional<ClubJoinQuitAttempt> opt = repository.findByClubNameAndUsername(clubName, username);
        if (opt.isPresent()) {
            ClubJoinQuitAttempt record = opt.get();
            if (record.getBannedUntil() != null && LocalDateTime.now().isBefore(record.getBannedUntil())) {
                return true;
            }
        }
        return false;
    }

    // Get remaining ban time in seconds
    public long getRemainingBanSeconds(String username, String clubName) {
        Optional<ClubJoinQuitAttempt> opt = repository.findByClubNameAndUsername(clubName, username);
        if (opt.isPresent()) {
            ClubJoinQuitAttempt record = opt.get();
            if (record.getBannedUntil() != null) {
                return Duration.between(LocalDateTime.now(), record.getBannedUntil()).getSeconds();
            }
        }
        return 0;
    }

    // If the ban period has expired, reset the counter for a fresh start.
    public void resetIfExpired(String username, String clubName) {
        Optional<ClubJoinQuitAttempt> opt = repository.findByClubNameAndUsername(clubName, username);
        if (opt.isPresent()) {
            ClubJoinQuitAttempt record = opt.get();
            if (record.getBannedUntil() != null && LocalDateTime.now().isAfter(record.getBannedUntil())) {
                record.setAttemptCount(0);
                record.setBannedUntil(null);
                repository.save(record);
            }
        }
    }

    // Record a quit attempt; if attempts reach the threshold, ban the user for BAN_DURATION_MINUTES.
    public void recordQuit(String username, String clubName) {
        Optional<ClubJoinQuitAttempt> opt = repository.findByClubNameAndUsername(clubName, username);
        ClubJoinQuitAttempt record;
        if (opt.isPresent()) {
            record = opt.get();
        } else {
            record = new ClubJoinQuitAttempt();
            record.setClubName(clubName);
            record.setUsername(username);
            record.setAttemptCount(0);
        }
        record.setAttemptCount(record.getAttemptCount() + 1);
        if (record.getAttemptCount() >= MAX_ATTEMPTS) {
            record.setBannedUntil(LocalDateTime.now().plusMinutes(BAN_DURATION_MINUTES));
            record.setAttemptCount(0); // reset counter after applying ban
        }
        repository.save(record);
    }
}
