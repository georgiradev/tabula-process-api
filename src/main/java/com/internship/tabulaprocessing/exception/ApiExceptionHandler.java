package com.internship.tabulaprocessing.exception;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.transaction.NotSupportedException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {
        BindingExceptionResponse error =
                new BindingExceptionResponse(
                        HttpStatus.BAD_REQUEST.value(), ex.getBindingResult(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(EntityNotFoundException ex) {
        ApiExceptionResponse exception =
                new ApiExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyPresentException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(EntityAlreadyPresentException ex) {

        ApiExceptionResponse response =
                new ApiExceptionResponse(ex.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(NumberFormatException ex) {
        ApiExceptionResponse exception =
                new ApiExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(ConstraintViolationException ex) {
        ApiExceptionResponse exception =
                new ApiExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotSupportedException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(NotSupportedException ex) {
        ApiExceptionResponse exception =
                new ApiExceptionResponse(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(PropertyReferenceException ex) {
        ApiExceptionResponse exceptionResponse =
                new ApiExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<BindingExceptionResponse> handleException(BindException ex) {
        BindingExceptionResponse error =
                new BindingExceptionResponse(
                        HttpStatus.BAD_REQUEST.value(), ex.getBindingResult(), LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

}
