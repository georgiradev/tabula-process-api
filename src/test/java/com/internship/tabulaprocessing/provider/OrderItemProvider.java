package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.OrderItem;

import java.math.BigDecimal;

public class OrderItemProvider {

  public static OrderItem getOrderItemInstance() {
    OrderItem orderItem = new OrderItem();

    orderItem.setId(1);
    orderItem.setNote("Some note");
    orderItem.setWidth(5.65);
    orderItem.setHeight(5.60);
    orderItem.setCount(5);
    orderItem.setPricePerPiece(BigDecimal.valueOf(orderItem.getWidth() * orderItem.getHeight()));

    return orderItem;
  }
}
