package com.qflow.server.controller;

import com.qflow.server.entity.exceptions.*;
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

    @ExceptionHandler(UserIsNotAdminException.class)
    public final ResponseEntity<String> UserIsNotAdminException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsNotInQueue.class)
    public final ResponseEntity<String> UserIsNotInQueue(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QueueLockedException.class)
    public final ResponseEntity<String> QueueLockedException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
