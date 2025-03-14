package org.example.studentprotal.controller;


import org.example.studentprotal.dto.LoginDto;
import org.example.studentprotal.dto.RegisterDto;
import org.example.studentprotal.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private  final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        System.out.println("registerDto =*****************************************************"+ registerDto);
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        System.out.println("LoginDto: " + loginDto);
        String response = authService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
