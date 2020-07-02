package com.internship.tabulaprocessing.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "time_off_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Enumerated(EnumType.STRING)
  private TypeName name;

  private Boolean isPaid;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TimeOffType that = (TimeOffType) o;
    return id == that.id &&
            isPaid == that.isPaid &&
            name == that.name;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, isPaid);
  }
}
