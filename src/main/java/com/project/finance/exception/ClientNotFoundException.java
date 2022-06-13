package com.project.finance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(String message) {
        super(String.format("Failed for %s", message));
    }
}
