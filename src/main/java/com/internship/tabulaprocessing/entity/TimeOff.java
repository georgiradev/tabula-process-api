package com.internship.tabulaprocessing.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "id")
    private TimeOffTypes timeOffTypeId;*/

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private TimeOffStatus status;

    private String comment;

    @ManyToOne (fetch = FetchType.LAZY)
    private Employee employee;

    @ManyToOne (fetch = FetchType.LAZY)
    private Employee approver;


}
