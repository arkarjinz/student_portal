package org.example.studentprotal.dao;

import org.example.studentprotal.dto.ClubInfo;
import org.example.studentprotal.entity.Club;
import org.example.studentprotal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ClubDao extends JpaRepository<Club, Integer> {

    @Query("""
        select new org.example.studentprotal.dto.ClubInfo(
            c.clubName, c.description, c.clubImage, count(s.id)
        )
        from Club c join c.studentClubs sc join sc.student s 
        group by c.clubName, c.description, c.clubImage
        """)
    List<ClubInfo> findClubInfoByClubName();

    @Query("""
        select s from Student s join s.studentClubs sc join sc.club c where c.clubName = ?1
        """)
    List<Student> findStudentByClubName(String clubName);

    // Finder for club by its name
    Optional<Club> findByClubName(String clubName);
}
