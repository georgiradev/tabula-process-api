package com.internship.tabulaprocessing.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class BindingExceptionResponse {
  private int status;
  private Map<String, String> errors;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  public BindingExceptionResponse(int status, BindingResult result, LocalDateTime timestamp) {
    this.status = status;
    this.timestamp = timestamp;
    Map<String, String> errorMap = new LinkedHashMap<>();
    result
        .getFieldErrors()
        .forEach(fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
    this.errors = errorMap;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Map<String, String> getErrors() {
    return errors;
  }

  public void setErrors(Map<String, String> errors) {
    this.errors = errors;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}
