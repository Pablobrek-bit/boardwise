package com.henrique.pablo.BoardWise.shared.exception;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends RuntimeException {

    public HttpStatus status;

    public RoleNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}
