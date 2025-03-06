package org.example.studentprotal.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UsernameAlreadyExistException.class, StudentNumberAlreadyExistException.class,
    EntityNotFoundException.class})
    public ResponseEntity handleException(Exception ex) throws Exception{
        System.out.println("error message::"+ ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
