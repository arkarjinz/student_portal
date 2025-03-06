package org.example.studentprotal.dao;

import org.example.studentprotal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentDao extends JpaRepository<Student, Integer> {

    @Query("""
select s.id from Student s where s.username = ?1
""")
    int findIdByUserName(String username);

    Optional<Student> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByStudentNumber(String studentNumber);

    @Query("""
select s.profileImage from Student s where s.username= ?1
""")
    String getProfileImageByStudentUserName(String userName);
}
