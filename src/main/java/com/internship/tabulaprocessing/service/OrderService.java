package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.OrderItem;
import com.internship.tabulaprocessing.event.OrderCreatedEvent;
import com.internship.tabulaprocessing.event.OrderUpdatedEvent;
import com.internship.tabulaprocessing.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

  private OrderRepository orderRepository;
  private ProcessStageService processStageService;
  private CustomerService customerService;
  private OrderItemService orderItemService;
  private ApplicationEventPublisher publisher;

  @Autowired
  public OrderService(
      OrderRepository orderRepository,
      ProcessStageService processStageService,
      CustomerService customerService,
      OrderItemService orderItemService,
      ApplicationEventPublisher publisher) {
    this.orderRepository = orderRepository;
    this.processStageService = processStageService;
    this.customerService = customerService;
    this.orderItemService = orderItemService;
    this.publisher = publisher;
  }

  public Order create(Order order) {

    order.setDateTimeCreated(LocalDateTime.now());
    order.setCustomer(customerService.find(order.getCustomerId()).get());
    order.setOrderItems(this.setOrderItems(order.getOrderItemIds(), order));
    order.setProcessStage(processStageService.findFirstStageOfProcess(order.getProcessId()));
    order.setPrice(this.calculateOrderPrice(order.getOrderItems()));

    publisher.publishEvent(new OrderCreatedEvent(this, orderRepository.save(order)));

    return order;
  }

  public Order getOneById(int id) {
    Optional<Order> orderOptional = orderRepository.findById(id);

    if (!orderOptional.isPresent()) {
      throw new EntityNotFoundException("Order with id %s, not found " + id);
    }

    return orderOptional.get();
  }

  public Order update(Order order, int id) {

    Order oldOrder = this.getOneById(id);

    order.setId(id);
    order.setDateTimeCreated(oldOrder.getDateTimeCreated());
    order.setCustomer(customerService.find(order.getCustomerId()).get());
    order.setOrderItems(this.setOrderItems(order.getOrderItemIds(), order));
    this.updateProcessStage(order.getProcessStageId(), order);
    order.setPrice(this.calculateOrderPrice(order.getOrderItems()));
    orderRepository.save(order);

    return order;
  }

  public Order patch(Order order) {

    if (order.getCustomerId() != null) {
      order.setCustomer(this.customerService.find(order.getCustomerId()).get());
    }
    if (order.getProcessStageId() != null) {
      this.updateProcessStage(order.getProcessStageId(), order);
    }
    if (order.getOrderItemIds() != null) {
      this.setOrderItems(order.getOrderItemIds(), order);
      order.setPrice(this.calculateOrderPrice(order.getOrderItems()));
    }
    return orderRepository.save(order);
  }

  public void delete(int id) {
    Optional<Order> orderOptional = orderRepository.findById(id);
    if (!orderOptional.isPresent()) {
      throw new EntityNotFoundException("A order with id: " + id + " does not exist");
    }
    orderRepository.deleteById(id);
  }

  public Page<Order> findAll(Pageable pageable) {
    return orderRepository.findAll(pageable);
  }

  /**
   * Sends a notification to the client via websocket listening on /notifications/customer/{customerId} endpoint
   * Notification is sent whenever process Stage of an order is changed;
   */

  private void updateProcessStage(Integer processStageId, Order order) {
    order.setProcessStage(processStageService.findById(processStageId));
    publisher.publishEvent(new OrderUpdatedEvent(this,order));
  }

  private List<OrderItem> setOrderItems(List<Integer> orderItems, Order order) {


    List<OrderItem> orderItemList = new ArrayList<>();
    for (var id : orderItems) {
      OrderItem orderItem = orderItemService.findById(id).get();
      orderItem.setOrder(order);
      orderItemList.add(orderItem);
    }
    return orderItemList;
  }

  private BigDecimal calculateOrderPrice(List<OrderItem> orderItems) {

    BigDecimal bigDecimal = BigDecimal.ZERO;
    for (var item : orderItems) {
      bigDecimal = bigDecimal.add(item.getPricePerPiece());
    }
    return bigDecimal;
  }
}
