package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.OrderItem;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.repository.MediaRepository;
import com.internship.tabulaprocessing.repository.OrderItemRepository;
import com.internship.tabulaprocessing.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

  private final MediaRepository mediaRepository;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  public Optional<OrderItem> save(OrderItem orderItem) {

    List<OrderItem> foundOrderItems =
        orderItemRepository.findIfPresent(
            orderItem.getOrder().getId(),
            orderItem.getMedia().getId(),
            orderItem.getWidth(),
            orderItem.getHeight());

    if (!foundOrderItems.isEmpty()) {
      throw new EntityAlreadyPresentException("Order item already in database");
    }
    validateOrderItem(orderItem);
    calculatePrice(orderItem);

    return Optional.of(orderItemRepository.save(orderItem));
  }

  private void validateOrderItem(OrderItem orderItem) {
    Optional<Media> foundMedia = mediaRepository.findById(orderItem.getMedia().getId());

    if (!foundMedia.isPresent()) {
      throw new EntityNotFoundException("Media not found with id " + orderItem.getMedia().getId());
    }
    orderItem.setMedia(foundMedia.get());
    Optional<Order> foundOrder = orderRepository.findById(orderItem.getOrder().getId());

    if (!foundOrder.isPresent()) {
      throw new EntityNotFoundException("Order not found with id " + orderItem.getOrder().getId());
    }
    orderItem.setOrder(foundOrder.get());
  }

  private void calculatePrice(OrderItem orderItem) {
    // Price formula = width x height x price per piece

    orderItem.setPricePerPiece(
        orderItem
            .getPricePerPiece()
            .multiply(BigDecimal.valueOf(orderItem.getHeight()).multiply(BigDecimal.valueOf(orderItem.getWidth()))));
  }

  public Optional<OrderItem> findById(int id) {
    return Optional.of(
        orderItemRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order item not found with id " + id)));
  }

  public List<OrderItem> findAll(int pageNo) {
    Pageable paging = PageRequest.of(pageNo, 10);
    Page<OrderItem> pagedResult = orderItemRepository.findAll(paging);

    if (pagedResult.hasContent()) {
      return pagedResult.getContent();
    } else {
      return Collections.emptyList();
    }
  }

  public Optional<OrderItem> update(int id, OrderItem orderItem) {
    validateOrderItem(orderItem);
    Optional<OrderItem> foundOrderItem = orderItemRepository.findById(id);

    if(!foundOrderItem.isPresent()) {
      throw new EntityNotFoundException("Order item not found with id " + orderItem.getId());
    }
    OrderItem orderItemToUpdate = foundOrderItem.get();

    orderItemToUpdate.setOrder(orderItem.getOrder());
    orderItemToUpdate.setMedia(orderItem.getMedia());
    orderItemToUpdate.setCount(orderItem.getCount());
    orderItemToUpdate.setHeight(orderItem.getHeight());
    orderItemToUpdate.setWidth(orderItem.getWidth());
    orderItemToUpdate.setNote(orderItem.getNote());
    calculatePrice(orderItemToUpdate);

    return Optional.of(orderItemRepository.saveAndFlush(orderItemToUpdate));
  }

  public void delete(int id) {
    findById(id);
    orderItemRepository.deleteById(id);
  }
}
