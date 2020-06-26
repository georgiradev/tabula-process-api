package com.internship.tabulaprocessing.exception;

public class CompanyConflictException extends EntityAlreadyPresentException {

  public CompanyConflictException(String message) {
    super(message);
  }
}
