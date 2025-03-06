package org.example.studentprotal.controller;


import org.example.studentprotal.dto.PostDto;
import org.example.studentprotal.dto.StudentDto;
import org.example.studentprotal.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-portal")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    //http://localhost:8080/api/student-portal/id/name
    record LoginUserId(int id){}
    @GetMapping("/id/{name}")
    public ResponseEntity<LoginUserId> findIdByLoggedInUserName(@PathVariable("name")String userName){
        return ResponseEntity.ok(new LoginUserId(studentService.findStudentIdByUsername(userName)));
    }

    //http://localhost:8080/api/student-portal/student/-----
    @GetMapping("/student/{username}")
    public StudentDto findStudentByUserName(@PathVariable("username") String username) {
        return studentService.findStudentByUserName(username);
    }
    @GetMapping("/profile-image/{username}")
    public String getProfileImageByStudentUserName(@PathVariable("username") String username) {
        return studentService.getProfileImageByStudentUserName(username);
    }


    @GetMapping("/students")
    public List<StudentDto> findAllStudents(){
        return studentService.findAllStudents();
    }
    @GetMapping("/students/{id}")
    public StudentDto getStudentById(@PathVariable("id") int id){
        return studentService.getStudentById(id);
    }

    @GetMapping("/posts")
    public List<PostDto> findAllPosts(){
        return studentService.findAllPosts();
    }


    @PostMapping("/posts/{id}")
    public String createPost(@RequestBody PostDto postDto, @PathVariable("id")int studentId) {
        return studentService.createPost(postDto, studentId);
    }
}
