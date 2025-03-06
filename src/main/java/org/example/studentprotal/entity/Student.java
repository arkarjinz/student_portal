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
    public List<StudentClub> getStudentClubs() {
        return studentClubs;
    }

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Post> posts=
            new ArrayList<Post>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<StudentClub> studentClubs=
            new ArrayList<>();

    public void addStudentClub(StudentClub studentClub) {
        studentClub.setStudent(this);
        studentClubs.add(studentClub);
    }


    public void addPost(Post post) {
        post.setStudent(this);
        posts.add(post);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getRoseCount() {
        return roseCount;
    }

    public void setRoseCount(int roseCount) {
        this.roseCount = roseCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles=
            new HashSet<Role>();

    public void addRole(Role role) {
        roles.add(role);
    }



    public Student() {

    }
    public Student( String name, String username, String password, String studentNumber, String year, String semester) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.studentNumber = studentNumber;
        this.year = year;
        this.semester = semester;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
