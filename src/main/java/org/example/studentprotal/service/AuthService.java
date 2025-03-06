package org.example.studentprotal.service;

import org.example.studentprotal.dao.RoleDao;
import org.example.studentprotal.dao.StudentDao;
import org.example.studentprotal.dto.LoginDto;
import org.example.studentprotal.dto.RegisterDto;
import org.example.studentprotal.entity.Role;
import org.example.studentprotal.entity.Student;
import org.example.studentprotal.exception.StudentNumberAlreadyExistException;
import org.example.studentprotal.exception.UsernameAlreadyExistException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final StudentDao studentDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthService(StudentDao studentDao, RoleDao roleDao, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.studentDao = studentDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterDto registerDto){
        if(studentDao.existsByStudentNumber(registerDto.getStudentNumber())){
            throw new StudentNumberAlreadyExistException();
        }
        if(studentDao.existsByUsername(registerDto.getUsername())){
            throw new UsernameAlreadyExistException();
        }


        Role userRole= roleDao.findByRoleName("ROLE_USER").get();
        Student student=new Student(registerDto.getName(),
                registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()),
                registerDto.getStudentNumber(),
                registerDto.getYear(),
                registerDto.getSemester());
        student.setProfileImage(registerDto.getProfileImage());
        student.addRole(userRole);
        studentDao.save(student);
        return "Register Success";
    }

    public String login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Student loginUser = studentDao.findByUsername(loginDto.getUsername())
                .get();
        StringBuilder sb=new StringBuilder();
        for(Role role: loginUser.getRoles()) {
            sb.append(role.getRoleName());
            sb.append(" ");
        }

        return sb.toString();

    }

}


