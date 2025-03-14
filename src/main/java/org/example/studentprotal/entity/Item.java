package org.example.studentprotal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    // Remove imageUrl field
    // Add a new field to store the image as binary data
    @Lob
    private byte[] image;

    private boolean isFound;

    @ManyToOne
    private Student student;

    // Custom constructor (if needed)
    public Item(String title, String description, byte[] image, boolean isFound) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.isFound = isFound;
    }

    public void addStudent(Student student) {
        this.student = student;
    }
}
