package com.henrique.pablo.BoardWise.shared.exception;

import org.springframework.http.HttpStatus;

public class IdNotFoundException extends RuntimeException {

    public HttpStatus status;

    public IdNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
