package com.internship.tabulaprocessing.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "worklogs")
public class WorkLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="hours_spent")
    private double hoursSpent;

    @CreatedDate
    @Column(name="created_datetime")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(name="updated_datetime")
    private LocalDateTime updatedDateTime;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private Employee employee;

    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private TrackingHistory trackingHistory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkLog workLog = (WorkLog) o;
        return  id == workLog.id &&
                Objects.equals(employee, workLog.employee) &&
                Objects.equals(trackingHistory, workLog.trackingHistory) &&
                Objects.equals(hoursSpent, workLog.hoursSpent) &&
                Objects.equals(createdDateTime, workLog.createdDateTime) &&
                Objects.equals(updatedDateTime, workLog.updatedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employee, trackingHistory, hoursSpent,
                createdDateTime, updatedDateTime);
    }
}
