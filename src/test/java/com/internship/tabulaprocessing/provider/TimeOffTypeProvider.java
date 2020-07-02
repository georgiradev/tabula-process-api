package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.TimeOffType;
import com.internship.tabulaprocessing.entity.TypeName;

import java.util.Collections;
import java.util.List;

public class TimeOffTypeProvider {
  public static TimeOffType getTimeOffTypeInstance() {
    TimeOffType timeOffType = new TimeOffType();

    timeOffType.setId(1);
    timeOffType.setName(TypeName.PARENTAL_LEAVE);
    timeOffType.setIsPaid(true);

    return timeOffType;
  }

  public static List<TimeOffType> getTimeOffTypesInstance() {
    return Collections.singletonList(getTimeOffTypeInstance());
  }
}
