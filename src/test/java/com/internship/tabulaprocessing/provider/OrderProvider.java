package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.dto.OrderRequestDto;
import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.mapper.Mapper;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class OrderProvider {

  public static Order getPrePersitedOrder() {

    Order order = new Order();
    order.setCustomerId(1);
    order.setProcessId(1);
    order.setOrderItemIds(Arrays.asList(1, 2, 3));
    order.setNote("test");
    ProcessStage processStage = new ProcessStage();
    processStage.setId(1);
    order.setProcessStage(processStage);

    return order;
  }

  public static Order getPreUpdatedOrder(){

      Order order = new Order();
      order.setCustomer(CustomerProvider.getCustomerInstance());
      order.setProcessStage(ProcessStageEntityProvider.getPersistedStage("test"));
      order.setOrderItems(Arrays.asList(OrderItemProvider.getOrderItemInstance()));
      order.setNote("test note");
      order.setPrice(new BigDecimal(123));
      order.setProcessStageId(1);
      order.setCustomerId(1);
      order.setOrderItemIds(Arrays.asList(1,2,3));

      return order;

  }

  public static Order getOrderInstance() {

    Customer customer = new Customer();
    customer.setId(1);
    customer.setAccountId(1);
    customer.setPhone("0877722846");

    Order order = new Order();
    LocalDateTime currentDateTime = LocalDateTime.now();
    Timestamp timeStamp = Timestamp.valueOf(currentDateTime);

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
