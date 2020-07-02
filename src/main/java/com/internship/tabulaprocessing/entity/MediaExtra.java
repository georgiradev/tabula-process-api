package com.internship.tabulaprocessing.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "media_extra")
public class MediaExtra {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Size(min = 2, max = 40)
  @NotBlank(message = "Name can not be null")
  private String name;

  @NotNull(message = "Price must not be null")
  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 5, fraction = 2)
  private BigDecimal price;

  @JsonIgnoreProperties("mediaExtras")
  @ManyToMany(
      mappedBy = "mediaExtras",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
  private Set<Media> medias = new HashSet<>();
}
