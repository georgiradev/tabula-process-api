package com.internship.tabulaprocessing.exception;

import javax.persistence.EntityExistsException;

public class CompanyNotFoundException extends EntityExistsException {

  public CompanyNotFoundException(String message) {
    super(message);
  }
}
