package com.internship.tabulaprocessing.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiExceptionResponse {

  private final String message;
  private final HttpStatus httpStatus;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private final LocalDateTime timestamp;

  public ApiExceptionResponse(String message, HttpStatus httpStatus, LocalDateTime timestamp) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }
}
