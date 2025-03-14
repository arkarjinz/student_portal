package org.example.studentprotal.service;


import org.example.studentprotal.dao.StudentDao;
import org.example.studentprotal.dto.StudentDto;
import org.example.studentprotal.entity.Student;
import org.example.studentprotal.util.EntityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//buy rose
//send gift rose to student
@Service
public class RoseGiftService {
    private final StudentDao studentDao;

    public RoseGiftService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }
    @Transactional
    public StudentDto buyRose(String username,int roses){
        int balance=checkRoseCount(username);
        int updatedBalance=balance+roses;
        Student student=findStudentByUsername(username);
        student.setRoseCount(updatedBalance);
        studentDao.saveAndFlush(student);
        return EntityUtil.studentDto(student);
    }
    @Transactional
    public String sendRose(String fromUsername,String toUsername,int roses){
        Student fromStudent= findStudentByUsername(fromUsername);
        Student toStudent=findStudentByUsername(toUsername);
        int fromBalance=fromStudent.getRoseCount();
        if(fromBalance<roses){
            return "Insufficient balance";
        }
        int updatedFromBalance=fromBalance-roses;
        fromStudent.setRoseCount(updatedFromBalance);
        int toBalance=toStudent.getRoseCount();
        int updatedToBalance=toBalance+roses;
        toStudent.setRoseCount(updatedToBalance);
        studentDao.saveAndFlush(fromStudent);
        studentDao.saveAndFlush(toStudent);
        return "%s sends %d roses to %s.".formatted(fromUsername,roses,toUsername);
    }
    private Student findStudentByUsername(String username){
        return studentDao.findByUsername(username).get();
    }


    public int checkRoseCount(String username){
        return  findStudentByUsername(username).getRoseCount();
    }

    public String updateRoseCount(String username, int roses) {
        Student student=findStudentByUsername(username);
        student.setRoseCount(roses);
        studentDao.saveAndFlush(student);
        return "%s has %d roses.".formatted(username,roses);
    }
}
