package org.example.studentprotal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    private String studentNumber;

    private String year;

    private String semester;

    private int roseCount;

    private String profileImage;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<StudentClub> studentClubs = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    // Association helper for student clubs
    public void addStudentClub(StudentClub studentClub) {
        studentClub.setStudent(this);
        studentClubs.add(studentClub);
    }

    // Association helper for posts
    public void addPost(Post post) {
        post.setStudent(this);
        posts.add(post);
    }

    // NEW: Remove post helper to update both sides of the relationship
    public void removePost(Post post) {
        posts.remove(post);
        post.setStudent(null);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    // Constructors
    public Student() {
    }

    public Student(String name, String username, String password, String studentNumber, String year, String semester) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.studentNumber = studentNumber;
        this.year = year;
        this.semester = semester;
    }

    // Getters and setters for remaining fields are already provided.
}
