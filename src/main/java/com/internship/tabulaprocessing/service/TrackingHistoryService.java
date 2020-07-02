package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.TrackingHistory;
import com.internship.tabulaprocessing.repository.EmployeeRepository;
import com.internship.tabulaprocessing.repository.TrackingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TrackingHistoryService {

  private TrackingHistoryRepository repository;
  private OrderService orderService;
  private EmployeeRepository employeeRepository;
  private ProcessStageService processStageService;

  @Autowired
  public TrackingHistoryService(
      TrackingHistoryRepository repository,
      OrderService orderService,
      EmployeeRepository employeeRepository,
      ProcessStageService processStageService) {
    this.repository = repository;
    this.orderService = orderService;
    this.employeeRepository = employeeRepository;
    this.processStageService = processStageService;
  }

  public TrackingHistory create(Order order) {

    TrackingHistory trackingHistory = new TrackingHistory();
    trackingHistory.setOrder(order);
    trackingHistory.setProcessStage(order.getProcessStage());
    trackingHistory.setDateTimeUpdated(LocalDateTime.now());
    repository.save(trackingHistory);
    return trackingHistory;
  }

  public TrackingHistory findById(int id) {

    Optional<TrackingHistory> optionalTrackingHistory = repository.findById(id);
    if (optionalTrackingHistory.isEmpty()) {
      throw new EntityNotFoundException(
          String.format("Tracking History with id %s, not found.", id));
    }

    return optionalTrackingHistory.get();
  }

  public TrackingHistory update(TrackingHistory trackingHistory, int id) {

    TrackingHistory oldTrackingHistory = findById(id);

    trackingHistory.setDateTimeUpdated(oldTrackingHistory.getDateTimeUpdated());
    trackingHistory.setProcessStage(
        processStageService.findById(trackingHistory.getProcessStageId()));
    trackingHistory.setOrder(orderService.getOneById(trackingHistory.getOrderId()));
    trackingHistory.setAssignee(findEmployeeById(trackingHistory.getAssigneeId()));

    return trackingHistory;
  }

  public TrackingHistory patch(TrackingHistory trackingHistory) {

    if (trackingHistory.getAssigneeId() != null) {
      System.out.println("setting id of employee");
      trackingHistory.setAssignee(findEmployeeById(trackingHistory.getAssigneeId()));
    }

    if (trackingHistory.getOrderId() != null) {
      trackingHistory.setOrder(orderService.getOneById(trackingHistory.getOrderId()));
    }

    if (trackingHistory.getProcessStageId() != null) {
      trackingHistory.setProcessStage(
          processStageService.findById(trackingHistory.getProcessStageId()));
    }

    return repository.save(trackingHistory);
  }

  public Page<TrackingHistory> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public void delete(int id) {

    TrackingHistory trackingHistory = findById(id);
    repository.delete(trackingHistory);
  }

  public Page<TrackingHistory> findAllUnsaigned(Pageable pageable) {
    return repository.findAllByAssigneeNull(pageable);
  }

  public Page<TrackingHistory> findAllAssigned(Pageable pageable) {
    return repository.findAllByAssigneeNotNull(pageable);
  }

  public Page<TrackingHistory> findAllByOrderId(Pageable pageable, int id) {

    Order order = orderService.getOneById(id);

    return repository.findAllByOrder(pageable, order);
  }

  private Employee findEmployeeById(Integer assigneeId) {

    Optional<Employee> optionalEmployee = employeeRepository.findById(assigneeId);
    if (optionalEmployee.isEmpty()) {
      throw new EntityNotFoundException(
          String.format("Employee with id %s, not found", assigneeId));
    }
    return optionalEmployee.get();
  }
}
