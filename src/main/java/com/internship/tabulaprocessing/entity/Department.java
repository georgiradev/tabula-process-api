package com.internship.tabulaprocessing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "departmentEntity",fetch = FetchType.EAGER,cascade = {CascadeType.REMOVE})
    private List<ProcessStage> processStageList = new ArrayList<>();
}
