package org.example.studentprotal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private OffsetDateTime createdAt;

    // Like count field
    @Column(nullable = false)
    private int likeCount = 0;

    @ManyToOne
    private Student student;

    // Cascade likes so that deleting a post also deletes its likes
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> likes = new ArrayList<>();

    public Post() {}

    public Post(String title, String content, OffsetDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
