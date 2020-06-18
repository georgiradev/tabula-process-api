package com.internship.tabulaprocessing.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "process_stage")
@Getter
@Setter
@NoArgsConstructor
public class ProcessStage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;

    @OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "next_stage_id")
    private ProcessStage nextStageEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "department_id")
    private Department departmentEntity;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "process_id")
    private Process processEntity;

    @Transient
    private String nextStage;
    @Transient
    private String department;
    @Transient
    private String process;

}
