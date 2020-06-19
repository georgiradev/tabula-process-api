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
              + "WHERE oi.order_id = :orderId AND oi.media_id = :mediaId "
              + "AND oi.width = :width AND oi.height = :height",
      nativeQuery = true)
  List<OrderItem> findIfPresent(
      @Param("orderId") int orderId,
      @Param("mediaId") int mediaId,
      @Param("width") double width,
      @Param("height") double height);
}
