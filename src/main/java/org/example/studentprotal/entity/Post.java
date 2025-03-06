package org.example.studentprotal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 5, max = 30, message = "Title must be between 5 and 30 words.")
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    // Use OffsetDateTime to capture timezone offset
    private OffsetDateTime createdAt;

    @ManyToOne
    private Student student;

    public Post() {}

    public Post(String title, String content, OffsetDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
