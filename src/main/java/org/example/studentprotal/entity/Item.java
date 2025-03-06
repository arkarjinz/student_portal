package org.example.studentprotal.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    private String imageUrl;
    private boolean isFound;


    public Item() {
    }


    public Item(String title, String description, String imageUrl, boolean isFound) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isFound = isFound;
    }
    @ManyToOne
    private Student student;

    public void addStudent(Student student) {
        this.student = student;
    }

}
