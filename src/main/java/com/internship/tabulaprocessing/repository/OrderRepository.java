package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  @Query(
      value = "SELECT id FROM orders AS o WHERE o.customer_id = :customer_id",
      nativeQuery = true)
  List<Integer> findOrdersByCustomerId(@Param("customer_id") int customerId);
}
