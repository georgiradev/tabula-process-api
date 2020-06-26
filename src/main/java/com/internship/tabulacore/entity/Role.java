package com.internship.tabulacore.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Data
@NoArgsConstructor
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts = new HashSet<>();
}
