package com.logout.backend.advice;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.logout.backend.dto.error.ErrorEntity;
import com.logout.backend.dto.error.ExpiredJwt;
import com.logout.backend.dto.error.IllegalArgument;
import com.logout.backend.dto.error.UsernameNotFound;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ApplicationControllerAdivce {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ EntityNotFoundException.class })
    public ErrorEntity handleException(EntityNotFoundException exception) {
        return new ErrorEntity(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IllegalArgumentException.class })
    public IllegalArgument handleIllegalArgument(IllegalArgumentException exception) {
        return new IllegalArgument(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ UsernameNotFoundException.class })
    public UsernameNotFound handeUsernameNotFound(UsernameNotFoundException exception) {
        return new UsernameNotFound(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ ExpiredJwtException.class })
    public ExpiredJwt handleExpiredJwt(ExpiredJwtException exception) {
        return new ExpiredJwt(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }
}
