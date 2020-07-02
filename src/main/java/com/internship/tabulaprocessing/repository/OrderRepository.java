package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.Customer;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.ProcessStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  List<Order> findAllByProcessStage(ProcessStage processStage);

  Page<Order> findAllByCustomer(Pageable pageable, Customer customer);
}
