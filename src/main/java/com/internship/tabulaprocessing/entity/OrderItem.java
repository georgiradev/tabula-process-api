package com.internship.tabulaprocessing.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private double width;

  private double height;

  private int count;

  private String note;

  @Column(name = "price_per_piece")
  private BigDecimal pricePerPiece;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "media_id")
  private Media media;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "order_id")
  private Order order;
}
