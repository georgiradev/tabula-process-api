package com.internship.tabulacore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String fullName;

  private String email;

  private String password;

  @CreatedDate
  private LocalDateTime datetimeCreated;

  @LastModifiedDate
  private LocalDateTime datetimeUpdated;

  @EqualsAndHashCode.Exclude
  @ManyToMany(fetch = FetchType.EAGER, cascade = {
          CascadeType.MERGE,
          CascadeType.PERSIST
  })
  @Fetch(FetchMode.JOIN)
  @JoinTable(name = "account_role",
          joinColumns = @JoinColumn(name = "account_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

}
