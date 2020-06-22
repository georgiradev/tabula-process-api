package com.internship.tabulaprocessing.entity;

import com.internship.tabulacore.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal ratePerHour;

    private int accountId;

    @ManyToOne
    private Department department;

    @Transient
    Account account;
}