package com.henrique.pablo.BoardWise.shared.exception;

import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends RuntimeException {

    public HttpStatus status;

    public ProjectNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}
