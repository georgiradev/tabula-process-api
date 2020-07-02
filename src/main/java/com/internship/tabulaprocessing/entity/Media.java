package com.internship.tabulaprocessing.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
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

  @Size(min = 2, max = 40)
  @NotBlank(message = "Name can not be null")
  private String name;

  @NotNull(message = "Price must not be null")
  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 5, fraction = 2)
  private BigDecimal price;

  @JsonIgnoreProperties("medias")
  @ManyToMany(
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
  @JoinTable(
      name = "media_extra_media",
      joinColumns = @JoinColumn(name = "media_id"),
      inverseJoinColumns = @JoinColumn(name = "media_extra_id"))
  private Set<MediaExtra> mediaExtras = new HashSet<>();

  @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems;

  public void calculatePrice() {
    if (mediaExtras != null) {
      for (MediaExtra mediaExtra : this.mediaExtras) {
        price = price.add(mediaExtra.getPrice());
      }
    }
  }

  public void removeExtrasPrice(){
    if (mediaExtras != null) {
      for (MediaExtra mediaExtra : this.mediaExtras) {
        price = price.subtract(mediaExtra.getPrice());
      }
    }
  }
}
