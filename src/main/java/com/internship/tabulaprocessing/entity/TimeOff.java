package com.internship.tabulaprocessing.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="timeOffs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @Enumerated(EnumType.STRING)
    private TimeOffType timeOffType;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private TimeOffStatus status;

    private String comment;

    @ManyToOne (cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    private Employee employee;

    @ManyToOne (cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    private Employee approver;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeOff timeOff = (TimeOff) o;
        return  id == timeOff.id &&
                Objects.equals(timeOffType, timeOff.timeOffType) &&
                Objects.equals(startDateTime, timeOff.startDateTime) &&
                Objects.equals(endDateTime, timeOff.endDateTime) &&
                status == timeOff.status &&
                Objects.equals(comment, timeOff.comment) &&
                Objects.equals(employee.getId(), timeOff.employee.getId()) &&
                Objects.equals(approver.getId(), timeOff.approver.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeOffType, startDateTime, endDateTime,
                status, comment, employee.getId(), approver.getId());
    }
}
