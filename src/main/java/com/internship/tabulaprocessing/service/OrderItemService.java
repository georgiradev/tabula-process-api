package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.OrderItem;
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import com.internship.tabulaprocessing.repository.MediaRepository;
import com.internship.tabulaprocessing.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

  private final MediaRepository mediaRepository;
  private final MediaExtraRepository mediaExtraRepository;
  private final OrderItemRepository orderItemRepository;

  public Optional<OrderItem> save(OrderItem orderItem) {

    validateOrderItem(orderItem);
    orderItem.setPricePerPiece(calculatePrice(orderItem));

    return Optional.of(orderItemRepository.save(orderItem));
  }

  private void validateOrderItem(OrderItem orderItem) {
    Optional<Media> foundMedia = mediaRepository.findById(orderItem.getMedia().getId());

    if (foundMedia.isEmpty()) {
      throw new EntityNotFoundException("Media not found with id " + orderItem.getMedia().getId());
    }

    orderItem.setMedia(foundMedia.get());
  }

  private BigDecimal calculatePrice(OrderItem orderItem) {
    // Price formula = width x height x (media.price + mediaExtra.price)

    Optional<Media> foundMedia = mediaRepository.findById(orderItem.getMedia().getId());
    BigDecimal mediaExtraPrice = BigDecimal.valueOf(0);

    for (MediaExtra mediaExtra : foundMedia.get().getMediaExtras()) {
      mediaExtraPrice = mediaExtraPrice.add(mediaExtra.getPrice());
    }

    BigDecimal width = BigDecimal.valueOf(orderItem.getWidth());
    BigDecimal height = BigDecimal.valueOf(orderItem.getHeight());
    BigDecimal pricePerPiece = foundMedia.get().getPrice().add(mediaExtraPrice);

    return width.multiply(height.multiply(pricePerPiece));
  }

  public OrderItem findById(int id) {
    return orderItemRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Order item not found with id " + id));
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
    orderItem.setId(foundOrderItem.get().getId());
    OrderItem currentOrderItem = foundOrderItem.get();

    if (orderItem.getHeight() != currentOrderItem.getHeight()
        || orderItem.getWidth() != currentOrderItem.getWidth()) {

      orderItem.setPricePerPiece(calculatePrice(orderItem));
    }

    return Optional.of(orderItemRepository.saveAndFlush(orderItem));
  }

  public void delete(int id) {
    findById(id);
    orderItemRepository.deleteById(id);
  }
}
