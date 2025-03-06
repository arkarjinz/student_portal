package org.example.studentprotal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StudentNumberAlreadyExistException extends ResponseStatusException {

    public StudentNumberAlreadyExistException() {
        super(HttpStatus.BAD_REQUEST,"Student number is already taken!");
    }
}
