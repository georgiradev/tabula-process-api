package com.internship.tabulaprocessing.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Stream;

@Getter
public enum TimeOffStatus {
    PENDING,
    REJECTED,
    APPROVED,
    PENDING_DELETION;

    @JsonCreator
    public static TimeOffStatus from(String s) {
        if (Stream.of(values()).map(Enum::name).noneMatch(name -> name.equals(s))) {
            throw new EntityNotFoundException(String
                    .format("The provided status doesnt exist [%s]",s));
        }
        return valueOf(s);
    }
}
