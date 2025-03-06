package org.example.studentprotal.service;


import jakarta.persistence.EntityExistsException;
import org.example.studentprotal.dao.PostDao;
import org.example.studentprotal.dao.StudentDao;
import org.example.studentprotal.dto.PostDto;
import org.example.studentprotal.dto.StudentDto;
import org.example.studentprotal.entity.Post;
import org.example.studentprotal.entity.Student;
import org.example.studentprotal.util.EntityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentDao studentDao;
    private final PostDao postDao;

    public List<StudentDto> findAllStudents() {
        return studentDao.findAll()
                .stream()
                .map(EntityUtil::studentDto)
                .collect(Collectors.toList());
    }
    public String getProfileImageByStudentUserName(String userName){
        return studentDao.getProfileImageByStudentUserName(userName);
    }

    public int findStudentIdByUsername(String username){
        return studentDao.findIdByUserName(username);
    }

    public StudentDto getStudentById(int id) {
        return EntityUtil.studentDto(findStudent(id));
    }

    public StudentService(StudentDao studentDao, PostDao postDao) {
        this.studentDao = studentDao;
        this.postDao = postDao;
    }
    @Transactional
    public String createPost(PostDto postDto, int studentId) {
        Student student=findStudent(studentId);
        student.setId(studentId);
        Post post=EntityUtil.toPost(postDto);

        student.addPost(postDao.save(post));
        return "successfully created post";
    }
    public List<PostDto> findAllPosts() {
//        return postDao.findAll()
//                .stream()
//                .map(EntityUtil::toPostDto)
//                .collect(Collectors.toList());
        return postDao.findAllPostDto();
    }
    private Student findStudent(int studentId) {
        return studentDao.findById(studentId)
                .orElseThrow(EntityExistsException::new);
    }
    public StudentDto findStudentByUserName(String username){
        return EntityUtil.studentDto(studentDao.findByUsername(username).get());
    }
}
