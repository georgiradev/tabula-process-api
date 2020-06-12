package com.internship.tabulaprocessing.exception;

import javax.persistence.EntityExistsException;

public class CompanyConflictException extends EntityExistsException {

    public CompanyConflictException(String message) {
        super(message);
    }
}
