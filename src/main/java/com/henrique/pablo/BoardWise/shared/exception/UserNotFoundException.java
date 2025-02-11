package com.henrique.pablo.BoardWise.shared.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {

    public HttpStatus status;

    public UserNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}
