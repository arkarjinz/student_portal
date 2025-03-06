package org.example.studentprotal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "clubs")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String clubName;
    private String description;
    private BigDecimal membersCount;

    // Field for club image URL
    private String clubImage;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<StudentClub> studentClubs = new ArrayList<>();

    public void addStudentClub(StudentClub studentClub) {
        studentClub.setClub(this);
        studentClubs.add(studentClub);
    }

    public Club(String description, String clubName, String clubImage) {
        this.description = description;
        this.clubName = clubName;
        this.clubImage = clubImage;
    }
}
