package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.OrderItem;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.repository.MediaRepository;
import com.internship.tabulaprocessing.repository.OrderItemRepository;
import com.internship.tabulaprocessing.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

  private final MediaRepository mediaRepository;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  public Optional<OrderItem> save(OrderItem orderItem) {

    validateOrderItem(orderItem);
    calculatePrice(orderItem, orderItem.getPricePerPiece());

    return Optional.of(orderItemRepository.save(orderItem));
  }

  private void validateOrderItem(OrderItem orderItem) {
    Optional<Media> foundMedia = mediaRepository.findById(orderItem.getMedia().getId());

    if (foundMedia.isEmpty()) {
      throw new EntityNotFoundException("Media not found with id " + orderItem.getMedia().getId());
    }

    orderItem.setMedia(foundMedia.get());
  }

  private void calculatePrice(OrderItem orderItem, BigDecimal pricePerPiece) {
    // Price formula = width x height x price per piece

    orderItem.setPricePerPiece(
        pricePerPiece.multiply(
            BigDecimal.valueOf(orderItem.getHeight())
                .multiply(BigDecimal.valueOf(orderItem.getWidth()))));
  }

  public Optional<OrderItem> findById(int id) {
    return Optional.of(
        orderItemRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order item not found with id " + id)));
  }

  public Page<OrderItem> findAll(QueryParameter queryParameter) {
    return orderItemRepository.findAll(queryParameter.getPageable());
  }

  public Optional<OrderItem> update(int id, OrderItem orderItem) {
    validateOrderItem(orderItem);
    Optional<OrderItem> foundOrderItem = orderItemRepository.findById(id);

    if (foundOrderItem.isEmpty()) {
      throw new EntityNotFoundException("Order item not found with id " + id);
    }
    OrderItem orderItemToUpdate = foundOrderItem.get();
    orderItemToUpdate.setOrder(orderItem.getOrder());
    orderItemToUpdate.setMedia(orderItem.getMedia());
    orderItemToUpdate.setCount(orderItem.getCount());
    orderItemToUpdate.setHeight(orderItem.getHeight());
    orderItemToUpdate.setWidth(orderItem.getWidth());
    orderItemToUpdate.setNote(orderItem.getNote());
    calculatePrice(orderItemToUpdate, orderItem.getPricePerPiece());

    return Optional.of(orderItemRepository.saveAndFlush(orderItemToUpdate));
  }

  public void delete(int id) {
    findById(id);
    orderItemRepository.deleteById(id);
  }
}
