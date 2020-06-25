package com.internship.tabulaprocessing.entity;

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

   /* @ManyToOne(fetch = FetchType.LAZY,
               cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    private TimeOffTypes timeOffType;*/

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private TimeOffStatus status;

    private String comment;

    @ManyToOne (fetch = FetchType.LAZY)
    private Employee employee;

    @ManyToOne (fetch = FetchType.LAZY)
    private Employee approver;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeOff timeOff = (TimeOff) o;
        return  id == timeOff.id &&
                Objects.equals(startDateTime, timeOff.startDateTime) &&
                Objects.equals(endDateTime, timeOff.endDateTime) &&
                status == timeOff.status &&
                Objects.equals(comment, timeOff.comment) &&
                Objects.equals(employee, timeOff.employee) &&
                Objects.equals(approver, timeOff.approver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDateTime, endDateTime, status, comment, employee, approver);
    }
}
