package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.Customer;
import com.internship.tabulaprocessing.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class OrderProvider {

  public static Order getOrderInstance() {
    // set the customer
    Customer customer = new Customer();
    customer.setId(1);
    customer.setAccountId(1);

    // set the order
    Order order = new Order();
    LocalDateTime currentDateTime = LocalDateTime.now();

    order.setId(1);
    order.setCustomer(customer);
    order.setDateTimeCreated(currentDateTime);
    order.setNote("Some message");
    order.setPrice(BigDecimal.valueOf(12.32));

    return order;
  }

  public static List<Order> getOrdersInstance() {
    return Collections.singletonList(getOrderInstance());
  }
}
