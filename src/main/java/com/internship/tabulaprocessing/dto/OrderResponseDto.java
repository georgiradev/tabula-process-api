package com.internship.tabulaprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private int id;

    private LocalDateTime dateTimeCreated;
    private BigDecimal price;
    private String note;
    private int customerId;
    private int processStageId;
    private ProcessStageResponseDTO processStage;
    private List<OrderItemResponseDto> orderItems;
}
