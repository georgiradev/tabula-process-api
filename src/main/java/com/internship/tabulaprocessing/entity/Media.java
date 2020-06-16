package com.internship.tabulaprocessing.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private BigDecimal price;

    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "media_extra_media",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "media_extra_id"))
    private Set<MediaExtra> mediaExtras = new HashSet<>();
}