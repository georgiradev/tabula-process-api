package com.internship.tabulaprocessing.entity;

import lombok.Getter;
import lombok.Setter;

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

  private BigDecimal pricePerPiece;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "media_id")
  private Media media;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;
}
