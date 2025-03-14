package org.example.studentprotal;

import jakarta.persistence.EntityNotFoundException;
import org.example.studentprotal.dao.*;
import org.example.studentprotal.entity.Club;
import org.example.studentprotal.entity.Student;
import org.example.studentprotal.entity.StudentClub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@EnableScheduling
@SpringBootApplication
public class StudentProtalApplication {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ClubDao clubDao;
    @Autowired
    private StudentClubDao studentClubDao;

    @Bean
    @Transactional
    @Profile("dev")
    public ApplicationRunner applicationRunner() {
        return r -> {

            clubDao.findClubInfoByClubName()
                    .forEach(c -> System.out.println(c));
            // Create a few clubs with local image paths.
//            Club club1 = new Club("A club for sports enthusiasts", "Sports Club", "/images/sports_club.jpg");
//            Club club2 = new Club("A club for music lovers", "Music Club", "/images/music_clubs.jpg");
//            Club club3 = new Club("A club for art fans", "Art Club", "/images/art_clubs.jpg");
//
//            clubDao.save(club1);
//            clubDao.save(club2);
//            clubDao.save(club3);
//
//            // Optionally, associate a student with one of the clubs.
//            Student student = studentDao.findByUsername("mary")
//                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
//            StudentClub sc = new StudentClub();
//            sc.setJoinDate(LocalDate.now());
//            sc.setStudent(student);
//            sc.setClub(club1);
//            studentClubDao.save(sc);
        };
    }

    // @Scheduled(fixedRate = 5000)
    public void everyFiveSecondsRun() {
        System.out.println("Every five seconds run");
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentProtalApplication.class, args);
    }
}
