package com.henrique.pablo.BoardWise.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorModel<T> {
    private final T error;
    private final String description;
    private final HttpStatus status;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime timestamp;

    public ErrorModel(T error, String description, HttpStatus status) {
        this.error = error;
        this.description = description;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
