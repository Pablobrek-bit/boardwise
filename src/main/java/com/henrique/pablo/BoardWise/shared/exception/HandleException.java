package com.henrique.pablo.BoardWise.shared.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.henrique.pablo.BoardWise.domain.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.henrique.pablo.BoardWise.application.dto.Error;

import java.util.List;

@ControllerAdvice
public class HandleException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        List<Error> errorException = e.getBindingResult().getFieldErrors().stream()
                .map(err -> new Error(err.getDefaultMessage(), err.getField()))
                .toList();

        ErrorModel<List<Error>> error = new ErrorModel<>(errorException,
                request.getDescription(false), HttpStatus.BAD_REQUEST);

        System.out.println(e.getMessage());

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Object> handleTokenExpiredException(TokenExpiredException e, WebRequest request) {
        ErrorModel<String> error = new ErrorModel<>(e.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST);

        System.out.println(e.getMessage());

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<Object> handleJWTVerificationException(JWTVerificationException e, WebRequest request) {
        ErrorModel<String> error = new ErrorModel<>(e.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST);

        System.out.println(e.getMessage());

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailEmailAlreadyExistsException(EmailAlreadyExistsException e, WebRequest request) {
        ErrorModel<String> error = new ErrorModel<>(e.getMessage(),
                request.getDescription(false), e.status);

        System.out.println(e.getMessage());

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<Object> handleIdNotFoundException(IdNotFoundException e, WebRequest request) {
        ErrorModel<String> error = new ErrorModel<>(e.getMessage(),
                request.getDescription(false), e.status);

        System.out.println(e.getMessage());

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException e, WebRequest request) {
        ErrorModel<String> error = new ErrorModel<>(e.getMessage(),
                request.getDescription(false), e.status);

        System.out.println(e.getMessage());

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e, WebRequest request) {
        ErrorModel<String> error = new ErrorModel<>(e.getMessage(),
                request.getDescription(false), e.status);

        System.out.println(e.getMessage());

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e, WebRequest request) {
        ErrorModel<String> error = new ErrorModel<>(e.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST);

        System.out.println(e.getMessage());

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, WebRequest request) {
        ErrorModel<String> error = new ErrorModel<>(e.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST);

        System.out.println(e.getMessage());

        return new ResponseEntity<>(error, error.getStatus());
    }

}
