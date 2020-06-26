package com.internship.tabulaprocessing.entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum TimeOffStatus {
    PENDING,
    REJECTED,
    APPROVED,
    PENDING_DELETION
}
