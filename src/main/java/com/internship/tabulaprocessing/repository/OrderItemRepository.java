package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

  @Query(
      value =
          "SELECT DISTINCT * FROM order_item AS oi "
              + "WHERE oi.order_id = :order_id AND oi.media_id = :media_id "
              + "AND oi.width = :width AND oi.height = :height",
      nativeQuery = true)
  List<OrderItem> findIfPresent(
      @Param("order_id") int orderId,
      @Param("media_id") int mediaId,
      @Param("width") double width,
      @Param("height") double height);
}
