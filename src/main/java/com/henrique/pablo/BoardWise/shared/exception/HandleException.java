package com.henrique.pablo.BoardWise.shared.exception;

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
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request){
        List<Error> errorException = e.getBindingResult().getFieldErrors().stream()
                .map(err -> new Error(err.getDefaultMessage(), err.getField()))
                .toList();

        ErrorModel<List<Error>> error = new ErrorModel<>(errorException,
                request.getDescription(false), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e, WebRequest request){
        ErrorModel<String> error = new ErrorModel<>(e.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, WebRequest request){
        ErrorModel<String> error = new ErrorModel<>(e.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, error.getStatus());
    }

}
