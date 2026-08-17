package com.logout.backend.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.logout.backend.dto.ErrorEntity;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ApplicationControllerAdivce {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ EntityNotFoundException.class })
    public ErrorEntity handleException(EntityNotFoundException exception) {
        return new ErrorEntity(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
}
