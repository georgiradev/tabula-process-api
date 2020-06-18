package com.internship.tabulaprocessing.exception;

import javax.persistence.EntityNotFoundException;

public class CompanyNotFoundException extends EntityNotFoundException {

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
