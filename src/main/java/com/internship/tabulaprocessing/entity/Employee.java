package com.internship.tabulaprocessing.entity;

import com.internship.tabulacore.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 5, fraction = 2)
  private BigDecimal ratePerHour;

  @Min(value = 0, message = "id cannot be less than zero")
  private int accountId;

  @ManyToOne(fetch = FetchType.LAZY,
              cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
  private Department department;

  @Transient
  Account account;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return id == employee.id &&
            accountId == employee.accountId &&
            Objects.equals(ratePerHour, employee.ratePerHour) &&
            Objects.equals(department, employee.department);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ratePerHour, accountId, department);
  }
}
