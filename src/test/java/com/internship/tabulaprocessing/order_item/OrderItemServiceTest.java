package com.internship.tabulaprocessing.order_item;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.provider.*;
import com.internship.tabulaprocessing.repository.MediaRepository;
import com.internship.tabulaprocessing.repository.OrderItemRepository;
import com.internship.tabulaprocessing.repository.OrderRepository;
import com.internship.tabulaprocessing.service.OrderItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {

  @InjectMocks private OrderItemService orderItemService;

  @Mock private MediaRepository mediaRepository;

  @Mock private OrderRepository orderRepository;

  @Mock private OrderItemRepository orderItemRepository;

  @Test
  void testOrderItemSave() {
    OrderItem orderItem = assembleObject();

    when(mediaRepository.findById(any(Integer.class)))
        .thenReturn(Optional.of(orderItem.getMedia()));
    when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

    assertEquals(Optional.of(orderItem), orderItemService.save(orderItem));
  }

  @Test
  void testOrderItemSaveWithNonExistingOrderMustFail() {
    OrderItem orderItem = assembleObject();

    when(mediaRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> orderItemService.save(orderItem));
  }

  @Test
  void testOrderItemSaveWithNonExistingMediaMustFail() {
    OrderItem orderItem = assembleObject();

    when(mediaRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> orderItemService.save(orderItem));
  }

  @Test
  void testOrderItemFindById() {
    OrderItem orderItem = assembleObject();

    when(orderItemRepository.findById(any(Integer.class)))
            .thenReturn(Optional.of(orderItem));

    assertEquals(orderItem, orderItemService.findById(orderItem.getId()));
  }

  @Test
  void testOrderItemFindByNonExistingIdShouldFail() {
    OrderItem orderItem = assembleObject();

    when(orderItemRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> orderItemService.findById(orderItem.getId()));
  }

  @Test
  void testFindAllOrderItems() {
    List<OrderItem> orderItems = Collections.singletonList(assembleObject());
    Page<OrderItem> paging = new PageImpl<>(orderItems);
    QueryParameter queryParameter = new QueryParameter();

    when(orderItemRepository.findAll(any(Pageable.class))).thenReturn(paging);

    assertEquals(paging, orderItemService.findAll(queryParameter));
  }

  @Test
  void testFindAllOrderItemsButNoContentFound() {
    List<OrderItem> orderItems = Collections.emptyList();
    Page<OrderItem> paging = new PageImpl<>(orderItems);
    QueryParameter queryParameter = new QueryParameter();

    when(orderItemRepository.findAll(any(Pageable.class))).thenReturn(paging);

    assertEquals(paging, orderItemService.findAll(queryParameter));
  }

  @Test
  void testUpdateOrderItem() {
    OrderItem orderItem = assembleObject();

    when(orderItemRepository.findById(orderItem.getId())).thenReturn(Optional.of(orderItem));
    when(orderItemRepository.saveAndFlush(any(OrderItem.class))).thenReturn(orderItem);
    when(mediaRepository.findById(any(Integer.class)))
            .thenReturn(Optional.of(orderItem.getMedia()));

    assertEquals(Optional.of(orderItem), orderItemService.update(1, orderItem));
  }

  @Test
  void testUpdateOrderItemByNonExistingIdShouldFail() {
    OrderItem orderItem = assembleObject();

    when(orderItemRepository.findById(orderItem.getId())).thenReturn(Optional.empty());
    when(mediaRepository.findById(any(Integer.class)))
            .thenReturn(Optional.of(orderItem.getMedia()));

    assertThrows(EntityNotFoundException.class, () -> orderItemService.update(orderItem.getId(), orderItem));
  }

  @Test
  void deleteOrderItem() {
    OrderItem orderItem = assembleObject();

    when(orderItemRepository.findById(orderItem.getId())).thenReturn(Optional.of(orderItem));
    orderItemService.delete(orderItem.getId());
    when(orderItemRepository.findById(orderItem.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> orderItemService.delete(orderItem.getId()));
  }

  @Test
  void deleteOrderItemByNonExistingIdShouldFail() {
    OrderItem orderItem = assembleObject();

    when(orderItemRepository.findById(orderItem.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> orderItemService.delete(orderItem.getId()));
  }

  OrderItem assembleObject() {
    OrderItem orderItem = OrderItemProvider.getOrderItemInstance();
    Media media = MediaProvider.getMediaInstance();
    MediaExtra mediaExtra = MediaExtraProvider.getMediaExtraInstance();
    Order order = OrderProvider.getOrderInstance();

    mediaExtra.setMedias(Collections.singleton(media));
    media.setMediaExtras(Collections.singleton(mediaExtra));
    media.setOrderItems(Collections.singletonList(orderItem));
    //order.setOrderItems(Collections.singletonList(orderItem));
    orderItem.setMedia(media);
    orderItem.setOrder(order);

    return orderItem;
  }
}
