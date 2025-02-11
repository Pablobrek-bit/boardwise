package com.henrique.pablo.BoardWise.shared.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends RuntimeException {

    public HttpStatus status;

    public EmailAlreadyExistsException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
