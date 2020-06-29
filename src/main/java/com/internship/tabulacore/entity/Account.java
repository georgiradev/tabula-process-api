package com.internship.tabulacore.entity;

import com.internship.tabulaprocessing.entity.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "full_name")
  private String fullName;

  private String email;

  private String password;

  @Column(name = "datetime_created")
  private LocalDateTime datetimeCreated;

  @Column(name = "datetime_updated")
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
