package org.example.studentprotal.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.studentprotal.dao.ClubDao;
import org.example.studentprotal.dao.StudentClubDao;
import org.example.studentprotal.dao.StudentDao;
import org.example.studentprotal.dto.ClubDto;
import org.example.studentprotal.dto.ClubInfo;
import org.example.studentprotal.entity.Club;
import org.example.studentprotal.entity.Student;
import org.example.studentprotal.entity.StudentClub;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubDao clubDao;
    private final StudentClubDao studentClubDao;
    private final StudentDao studentDao;
    private final ClubBanService clubBanService;

    @Transactional
    public String joinClub(String clubName, String studentName, String studentNumber) {
        Optional<Club> clubOpt = clubDao.findByClubName(clubName);
        if (!clubOpt.isPresent()) {
            throw new EntityNotFoundException("Club not found");
        }
        Club club = clubOpt.get();

        Optional<Student> studentOpt = studentDao.findByUsername(studentName);
        if (!studentOpt.isPresent()) {
            throw new EntityNotFoundException("Student not found");
        }
        Student student = studentOpt.get();

        // Reset the ban record if the ban period has expired
        clubBanService.resetIfExpired(studentName, clubName);

        // Check if the user is currently banned from joining
        if (clubBanService.isBanned(studentName, clubName)) {
            long remaining = clubBanService.getRemainingBanSeconds(studentName, clubName);
            return "You are banned from joining this club for " + remaining + " seconds.";
        }

        // Check if already a member
        boolean alreadyJoined = club.getStudentClubs().stream()
                .anyMatch(sc -> sc.getStudent().getUsername().equals(studentName));
        if (alreadyJoined) {
            return "already joined";
        }

        // Create the StudentClub association
        StudentClub studentClub = new StudentClub();
        studentClub.setJoinDate(LocalDate.now());
        studentClub.setStudent(student);
        studentClub.setClub(club);
        if (club.getMembersCount() == null) {
            club.setMembersCount(BigDecimal.ONE);
        } else {
            club.setMembersCount(club.getMembersCount().add(BigDecimal.ONE));
        }
        club.getStudentClubs().add(studentClub);
        student.addStudentClub(studentClub);
        studentClubDao.save(studentClub);

        // Do not reset the ban record on join—quit attempts persist.
        return "joined successfully";
    }

    @Transactional
    public String quitClub(String clubName, String studentName) {
        Optional<Club> clubOpt = clubDao.findByClubName(clubName);
        if (!clubOpt.isPresent()) {
            throw new EntityNotFoundException("Club not found");
        }
        Club club = clubOpt.get();

        Optional<Student> studentOpt = studentDao.findByUsername(studentName);
        if (!studentOpt.isPresent()) {
            throw new EntityNotFoundException("Student not found");
        }
        Student student = studentOpt.get();

        Optional<StudentClub> associationOpt = club.getStudentClubs().stream()
                .filter(sc -> sc.getStudent().getUsername().equals(studentName))
                .findFirst();
        if (!associationOpt.isPresent()) {
            return "not a member";
        }
        StudentClub studentClub = associationOpt.get();
        club.getStudentClubs().remove(studentClub);
        student.getStudentClubs().remove(studentClub);
        studentClubDao.delete(studentClub);
        if (club.getMembersCount() != null && club.getMembersCount().compareTo(BigDecimal.ZERO) > 0) {
            club.setMembersCount(club.getMembersCount().subtract(BigDecimal.ONE));
        }
        // Record the quit event to increment the join/quit counter and possibly trigger a ban.
        clubBanService.recordQuit(studentName, clubName);
        return "quit successfully";
    }

    // Other methods (createClub, updateClub, deleteClub, etc.) remain unchanged.
    @Transactional
    public String createClub(String clubName, String description, String clubImage) {
        Optional<Club> clubOpt = clubDao.findByClubName(clubName);
        if (clubOpt.isPresent()) {
            throw new EntityExistsException("Club already exists");
        }
        Club club = new Club(description, clubName, clubImage);
        clubDao.save(club);
        return "Club created successfully";
    }

    @Transactional
    public String deleteClub(String clubName) {
        Optional<Club> clubOpt = clubDao.findByClubName(clubName);
        if (!clubOpt.isPresent()) {
            throw new EntityNotFoundException("Club not found");
        }
        clubDao.delete(clubOpt.get());
        return "Club deleted successfully";
    }

    @Transactional
    public String updateClub(String clubName, String newDescription, String newClubImage) {
        Optional<Club> clubOpt = clubDao.findByClubName(clubName);
        if (!clubOpt.isPresent()) {
            throw new EntityNotFoundException("Club not found");
        }
        Club club = clubOpt.get();
        club.setDescription(newDescription);
        club.setClubImage(newClubImage);
        clubDao.save(club);
        return "Club updated successfully";
    }

    public List<ClubInfo> getClubInfo() {
        return clubDao.findClubInfoByClubName();
    }

    public List<ClubDto> getAllClubs() {
        return clubDao.findAll().stream()
                .map(club -> new ClubDto(
                        club.getClubName(),
                        club.getDescription(),
                        club.getClubImage(),
                        club.getStudentClubs().size()))
                .collect(Collectors.toList());
    }

    public List<Student> findStudentsByClubName(String clubName) {
        return clubDao.findStudentByClubName(clubName);
    }
}
