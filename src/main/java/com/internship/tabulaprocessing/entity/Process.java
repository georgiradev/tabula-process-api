package com.internship.tabulaprocessing.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "processEntity",fetch = FetchType.EAGER,cascade = {CascadeType.REMOVE})
    private List<ProcessStage> processStageList = new ArrayList<>();

}
