package com.internship.tabulaprocessing.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dateTimeCreated;

  private BigDecimal price;

  private String note;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST})
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST})
  @JoinColumn(name = "process_stage_id")
  private ProcessStage processStage;

  @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
  private List<OrderItem> orderItems;

  @Transient private Integer customerId;
  @Transient private Integer processStageId;
  @Transient private List<Integer> orderItemIds;
  @Transient private Integer processId;
}
