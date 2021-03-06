package com.internship.tabulaprocessing.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  @Column(name = "discount_rate")
  private double discountRate;

  private String address;

  private String country;

  private String city;

  @Column(name = "vat_number")
  private String vatNumber;

  @OneToMany(
      mappedBy = "company",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  private List<Customer> customers = new ArrayList<>();
}
