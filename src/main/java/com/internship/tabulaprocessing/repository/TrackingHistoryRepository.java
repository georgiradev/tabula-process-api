package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.TrackingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingHistoryRepository extends JpaRepository<TrackingHistory,Integer> {

    Page<TrackingHistory> findAllByAssigneeNull(Pageable pageable);
    Page<TrackingHistory> findAllByAssigneeNotNull(Pageable pageable);
    Page<TrackingHistory> findAllByOrder(Pageable pageable, Order order);
}
