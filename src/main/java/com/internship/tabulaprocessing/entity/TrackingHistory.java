package com.internship.tabulaprocessing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_history")
@Getter
@Setter
public class TrackingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "datetime_updated")
    private LocalDateTime dateTimeUpdated;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "process_stage_id")
    private ProcessStage processStage;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "assignee_id")
    private Employee assignee;

    @Transient
    private Integer orderId;
    @Transient
    private Integer processStageId;
    @Transient
    private Integer assigneeId;
}
