package org.example.studentprotal.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.example.studentprotal.dto.ClubDto;
import org.example.studentprotal.dto.ClubInfo;
import org.example.studentprotal.dto.StudentDto;
import org.example.studentprotal.entity.Student;
import org.example.studentprotal.service.ClubService;
import org.example.studentprotal.util.EntityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/club")
public class ClubController {
    private final ClubService clubService;

    @GetMapping("/club-info")
    public List<ClubInfo> getClubInfo() {
        return clubService.getClubInfo();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClubDto>> getAllClubs() {
        List<ClubDto> clubs = clubService.getAllClubs();
        return ResponseEntity.ok(clubs);
    }

    @GetMapping("/members/{clubName}")
    public ResponseEntity<List<StudentDto>> getMembersByClub(@PathVariable String clubName) {
        List<Student> students = clubService.findStudentsByClubName(clubName);
        List<StudentDto> dtos = students.stream()
                .map(EntityUtil::studentDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinClub(@RequestBody ClubJoinData joinData) {
        String responseString = clubService.joinClub(joinData.club_name(), joinData.student_name(), joinData.student_number());
        return ResponseEntity.ok(responseString);
    }

    @PostMapping("/quit")
    public ResponseEntity<String> quitClub(@RequestBody QuitRequest quitRequest) {
        String responseString = clubService.quitClub(quitRequest.club_name(), quitRequest.student_name());
        return ResponseEntity.ok(responseString);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createClub(@RequestBody ClubData clubData) {
        String responseString = clubService.createClub(clubData.clubName(), clubData.description(), clubData.clubImage());
        return ResponseEntity.ok(responseString);
    }

    @PutMapping("/update/{clubName}")
    public ResponseEntity<String> updateClub(@PathVariable String clubName, @RequestBody ClubUpdateData updateData) {
        String responseString = clubService.updateClub(clubName, updateData.newDescription(), updateData.newClubImage());
        return ResponseEntity.ok(responseString);
    }

    @DeleteMapping("/delete/{clubName}")
    public ResponseEntity<String> deleteClub(@PathVariable String clubName) {
        String responseString = clubService.deleteClub(clubName);
        return ResponseEntity.ok(responseString);
    }

    record ClubData(String clubName, String description, String clubImage) {}
    record ClubUpdateData(String newDescription, String newClubImage) {}
    record ClubJoinData(@JsonProperty("club_name") String club_name,
                        @JsonProperty("student_name") String student_name,
                        @JsonProperty("student_number") String student_number) {}
    record QuitRequest(@JsonProperty("club_name") String club_name,
                       @JsonProperty("student_name") String student_name) {}
}
