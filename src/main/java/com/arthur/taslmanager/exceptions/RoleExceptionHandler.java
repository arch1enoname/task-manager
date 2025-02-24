package com.arthur.taslmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RoleExceptionHandler {

    @ExceptionHandler(value = {TaskNotFoundException.class})
    public ResponseEntity<Object> handleProjectNotFoundException(RoleNotFoundException roleNotFoundException) {

        RoleException roleException = new RoleException(
                roleNotFoundException.getMessage(),
                roleNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(roleException, roleException.getHttpStatus());

    }
}
