package com.internship.tabulaprocessing.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum TypeName {
  VACATION,
  SICK_LEAVE,
  PAID_TIME_OFF,
  BEREAVEMENT_LEAVE,
  HOLIDAY_PAY,
  SABBATICAL,
  LEAVE_OF_ABSENCE,
  PARENTAL_LEAVE,
  MILITARY_LEAVE,
  PERSONAL_TIME_OFF,
  MEDICAL_LEAVE,
  FURLOUGH;

  @JsonCreator
  public static TypeName from(String s) {

    if (Stream.of(values()).map(Enum::name).noneMatch(name -> name.equals(s))) {
      return null;
    }

    return valueOf(s);
  }
}
