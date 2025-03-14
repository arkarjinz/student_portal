package org.example.studentprotal.dto;


public class StudentDto {
    private Integer id;
    private String name;
    private String username;
    private String studentNumber;
    private String year;
    private String semester;
    private int roseCount;
    private String profileImage;
    public StudentDto(){

    }
    public StudentDto(Integer id, String name, String username, String studentNumber, String year, String semester, int roseCount) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.studentNumber = studentNumber;
        this.year = year;
        this.semester = semester;
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

    public int getRoseCount() {
        return roseCount;
    }

    public void setRoseCount(int roseCount) {
        this.roseCount = roseCount;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
