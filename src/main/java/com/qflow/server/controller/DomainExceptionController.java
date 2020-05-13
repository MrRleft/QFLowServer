package com.qflow.server.controller;

import com.qflow.server.entity.exceptions.LoginNotSuccesfulException;
import com.qflow.server.entity.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DomainExceptionController {

    @ExceptionHandler(LoginNotSuccesfulException.class)
    public final ResponseEntity<String> handleLoginNotSuccessful(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<String> handleCreateNotSuccessful(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
