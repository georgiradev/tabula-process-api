package com.internship.tabulaprocessing.order;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.dto.OrderPatchRequestDTO;
import com.internship.tabulaprocessing.dto.OrderUpdateRequestDTO;
import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.provider.OrderItemProvider;
import com.internship.tabulaprocessing.provider.OrderProvider;
import com.internship.tabulaprocessing.provider.ProcessStageEntityProvider;
import com.internship.tabulaprocessing.repository.OrderRepository;
import com.internship.tabulaprocessing.service.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock private OrderRepository orderRepository;
  @Mock private ProcessServiceImpl processService;
  @Mock private ProcessStageService processStageService;
  @Mock private CustomerService customerService;
  @Mock private OrderItemService orderItemService;
  @Mock private ApplicationEventPublisher publisher;

  @InjectMocks private OrderService orderService;

  @Test
  public void testSuccessfulOrderPersistence() {

    Order preSavedOrder = OrderProvider.getPrePersitedOrder();
    when(customerService.find(anyInt())).thenReturn(new Customer());
    when(orderItemService.findById(anyInt()))
        .thenReturn(OrderItemProvider.getOrderItemInstance());
    when(processStageService.findFirstStageOfProcess(anyInt())).thenReturn(new ProcessStage());

    Order order = orderService.create(preSavedOrder);
    assertNotNull(order.getOrderItemIds());
    assertNotNull(order.getCustomer());
    assertNotNull(order.getProcessStage());

    assertDoesNotThrow(() -> orderService.create(preSavedOrder));
    assertEquals(
        order.getPrice(),
        (order.getOrderItems().get(0).getPricePerPiece())
            .multiply(new BigDecimal(order.getOrderItemIds().size())));
  }

  @Test
  public void testOrderPersistFailsIfOrderItemsAreAlreadyLinked() {

    OrderItem orderItem = OrderItemProvider.getOrderItemInstance();
    orderItem.setOrder(new Order());
    Order order = OrderProvider.getPrePersitedOrder();
    order.setOrderItems(Arrays.asList(orderItem));
    order.setProcessStageId(1);
    order.setCustomerId(1);
    when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
    when(processStageService.findById(anyInt())).thenReturn(new ProcessStage());
    when(customerService.find(anyInt())).thenReturn(new Customer());
    when(orderItemService.findById(anyInt())).thenReturn(orderItem);
    order = orderService.update(order,1);


    assertNotNull(order.getProcessStage());
    assertNotNull(order.getOrderItems());
    assertEquals(order.getId(),1);
  }

  @Test
  public void testUpdateOrder() {

    Order order = OrderProvider.getPreUpdatedOrder();

    when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
    when(customerService.find(anyInt())).thenReturn(new Customer());
    when(orderItemService.findById(anyInt()))
        .thenReturn(OrderItemProvider.getOrderItemInstance());
    when(processStageService.findById(anyInt())).thenReturn(new ProcessStage());

    assertDoesNotThrow(() -> orderService.update(order, 1));
  }

  @Test
  public void testGetById() {
    Order order = OrderProvider.getOrderInstance();

    when(orderRepository.findById(1)).thenReturn(Optional.of(order));
    Optional<Order> result = Optional.ofNullable(orderService.getOneById(1));

    Assertions.assertThat(result).isNotNull();
    result.ifPresent(value -> assertEquals(order, value));
  }

  @Test
  public void patchOrder() {

    Order order = OrderProvider.getPreUpdatedOrder();
    when(customerService.find(anyInt())).thenReturn(new Customer());
    when(orderItemService.findById(anyInt()))
        .thenReturn(OrderItemProvider.getOrderItemInstance());
    when(processStageService.findById(anyInt())).thenReturn(new ProcessStage());

    assertDoesNotThrow(() -> orderService.patch(order));
  }

  @Test
  public void testGetOrderByIdShouldFail() {
    Order order = OrderProvider.getOrderInstance();

    when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> orderService.getOneById(order.getId()));
  }

  @Test
  void testDeleteOrder() {
    Order order = OrderProvider.getOrderInstance();

    when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
    orderService.delete(order.getId());

    verify(orderRepository, times(1)).deleteById(order.getId());
  }

  @Test
  void testDeleteOrderShouldFail() {
    when(orderRepository.findById(1)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> orderService.delete(1));
  }

  @Test
  void testUpdateOrderShouldFailBecauseOfWrongId() {
    Order order = OrderProvider.getOrderInstance();
    OrderUpdateRequestDTO requestDTO = new OrderUpdateRequestDTO();

    when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> orderService.update(new Order(), 1));
  }

  @Test
  void testFindAll() {

    List<Order> orderList =
        Arrays.asList(
            OrderProvider.getOrderInstance(),
            OrderProvider.getOrderInstance(),
            OrderProvider.getOrderInstance(),
            OrderProvider.getOrderInstance(),
            OrderProvider.getOrderInstance(),
            OrderProvider.getOrderInstance(),
            OrderProvider.getOrderInstance());
    QueryParameter queryParameter = new QueryParameter();
    queryParameter.setSize(3);

    when(orderRepository.findAll(queryParameter.getPageable()))
        .thenReturn(new PageImpl<>(orderList, queryParameter.getPageable(), orderList.size()));

    Page<Order> page = orderService.findAll(queryParameter.getPageable());
    assertEquals(page.getSize(), queryParameter.getSize());
  }
}
