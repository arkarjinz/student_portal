package org.example.studentprotal.dao;

import org.example.studentprotal.entity.StudentClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentClubDao extends JpaRepository<StudentClub,
        Integer> {

    @Query("""
            select sc from StudentClub  sc where sc.club.clubName = ?1
            """)
    Optional<StudentClub> findStudentClubByStudentAndClub(String clubName);

}
