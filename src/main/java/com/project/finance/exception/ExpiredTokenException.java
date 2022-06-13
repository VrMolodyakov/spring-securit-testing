package com.project.finance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
