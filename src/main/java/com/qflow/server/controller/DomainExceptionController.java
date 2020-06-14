package com.qflow.server.controller;

import com.qflow.server.entity.exceptions.LoginNotSuccesfulException;
import com.qflow.server.entity.exceptions.QueueLockedException;
import com.qflow.server.entity.exceptions.UserAlreadyExistsException;
import com.qflow.server.entity.exceptions.UserNotInQueueException;
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

    @ExceptionHandler(UserNotInQueueException.class)
    public final ResponseEntity<String> UserNotInQueueException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QueueLockedException.class)
    public final ResponseEntity<String> QueueLockedException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
