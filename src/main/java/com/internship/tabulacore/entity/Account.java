package com.internship.tabulacore.entity;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name="accounts", catalog = "tabulacore")
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="account_id")
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
    @JoinTable(name = "accounts_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}

