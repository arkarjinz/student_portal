package org.example.studentprotal.service;

import jakarta.persistence.EntityNotFoundException;
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

    public StudentService(StudentDao studentDao, PostDao postDao) {
        this.studentDao = studentDao;
        this.postDao = postDao;
    }

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

    @Transactional
    public String createPost(PostDto postDto, int studentId) {
        Student student = findStudent(studentId);
        Post post = EntityUtil.toPost(postDto);
        student.addPost(postDao.save(post));
        return "Successfully created post";
    }

    public List<PostDto> findAllPosts() {
        return postDao.findAllPostDto();
    }

    private Student findStudent(int studentId) {
        return studentDao.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
    }

    public StudentDto findStudentByUserName(String username) {
        return EntityUtil.studentDto(studentDao.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Student not found with username: " + username)));
    }

    // NEW: Update student details by id.
    @Transactional
    public String updateStudent(int id, StudentDto studentDto) {
        Student student = studentDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        // Update fields as needed.
        student.setName(studentDto.getName());
        student.setUsername(studentDto.getUsername());
        student.setYear(studentDto.getYear());
        student.setStudentNumber(studentDto.getStudentNumber());
        student.setSemester(studentDto.getSemester());
        if (studentDto.getProfileImage() != null) {
            student.setProfileImage(studentDto.getProfileImage());
        }
        studentDao.save(student);
        return "Student updated successfully";
    }

    // NEW: Delete a student by id.
    @Transactional
    public String deleteStudent(int id) {
        if (!studentDao.existsById(id)) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentDao.deleteById(id);
        return "Student deleted successfully";
    }
}
