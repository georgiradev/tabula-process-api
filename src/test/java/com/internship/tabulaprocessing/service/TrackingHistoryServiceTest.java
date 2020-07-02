package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.TrackingHistory;
import com.internship.tabulaprocessing.provider.EmployeeProvider;
import com.internship.tabulaprocessing.provider.OrderProvider;
import com.internship.tabulaprocessing.provider.ProcessStageEntityProvider;
import com.internship.tabulaprocessing.provider.TrackingHistoryProvider;
import com.internship.tabulaprocessing.repository.EmployeeRepository;
import com.internship.tabulaprocessing.repository.OrderRepository;
import com.internship.tabulaprocessing.repository.TrackingHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackingHistoryServiceTest {

  @Mock private OrderService orderService;
  @Mock private EmployeeRepository employeeRepository;
  @Mock private ProcessStageService processStageService;
  @Mock private TrackingHistoryRepository repository;

  @InjectMocks private TrackingHistoryService service;

  @Test
  void create() {
    Order order = OrderProvider.getOrderInstance();
    order.setId(1);
    when(repository.save(any())).thenReturn(new TrackingHistory());

    TrackingHistory trackingHistory = service.create(order);
    assertNotNull(trackingHistory.getDateTimeUpdated());
    assertEquals(trackingHistory.getOrder().getId(), order.getId());
  }

  @Test
  void findById() {

    when(repository.findById(anyInt())).thenReturn(Optional.of(new TrackingHistory()));
    assertDoesNotThrow(() -> service.findById(1));
    when(repository.findById(anyInt())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> service.findById(1));
  }

  @Test
  void update() {

    when(repository.findById(anyInt()))
        .thenReturn(Optional.of(TrackingHistoryProvider.getTrackingHistory()));
    when(employeeRepository.findById(anyInt()))
        .thenReturn(Optional.of(EmployeeProvider.getEmployeeInstance()));
    when(orderService.getOneById(anyInt())).thenReturn(OrderProvider.getOrderInstance());
    when(processStageService.findById(anyInt()))
        .thenReturn(ProcessStageEntityProvider.getPersistedStage("test"));

    TrackingHistory trackingHistory =
        service.update(TrackingHistoryProvider.getTrackingHistory(), 1);

    assertDoesNotThrow(() -> service.update(trackingHistory, 1));
    assertNotNull(trackingHistory.getAssignee());
    assertNotNull(trackingHistory.getProcessStage());
    assertNotNull(trackingHistory.getOrder());
  }

  @Test
  void patch() {

    when(repository.findById(anyInt()))
        .thenReturn(Optional.of(TrackingHistoryProvider.getTrackingHistory()));
    when(employeeRepository.findById(anyInt()))
        .thenReturn(Optional.of(EmployeeProvider.getEmployeeInstance()));
    when(orderService.getOneById(anyInt())).thenReturn(OrderProvider.getOrderInstance());
    when(processStageService.findById(anyInt()))
        .thenReturn(ProcessStageEntityProvider.getPersistedStage("test"));

    TrackingHistory trackingHistory =
        service.update(TrackingHistoryProvider.getTrackingHistory(), 1);

    assertDoesNotThrow(() -> service.patch(trackingHistory));
    assertNotNull(trackingHistory.getAssignee());
    assertNotNull(trackingHistory.getProcessStage());
    assertNotNull(trackingHistory.getOrder());
  }

  @Test
  void findAll() {

    QueryParameter parameter = new QueryParameter();
    parameter.setSize(3);
    parameter.setPage(1);
    List<TrackingHistory> list =
        Arrays.asList(
            TrackingHistoryProvider.getTrackingHistory(),
            TrackingHistoryProvider.getTrackingHistory(),
            TrackingHistoryProvider.getTrackingHistory(),
            TrackingHistoryProvider.getTrackingHistory(),
            TrackingHistoryProvider.getTrackingHistory());

    Page<TrackingHistory> page =
        new PageImpl<>(list, parameter.getPageable(), list.size());
    when(repository.findAll(parameter.getPageable())).thenReturn(page);
    when(repository.findAllByAssigneeNotNull(parameter.getPageable())).thenReturn(page);
    when(repository.findAllByAssigneeNull(parameter.getPageable())).thenReturn(page);

    Page returnedPage = service.findAll(parameter.getPageable());
    assertEquals(returnedPage.getSize(),parameter.getSize());
    assertEquals(page.getTotalElements(),list.size());
    returnedPage = service.findAllAssigned(parameter.getPageable());
    assertEquals(returnedPage.getSize(),parameter.getSize());
    assertEquals(page.getTotalElements(),list.size());
    returnedPage = service.findAllUnsaigned(parameter.getPageable());
    assertEquals(returnedPage.getSize(),parameter.getSize());
    assertEquals(page.getTotalElements(),list.size());

  }

  @Test
  void delete(){

    when(repository.findById(any())).thenReturn(Optional.of(new TrackingHistory()));
    assertDoesNotThrow(() -> service.delete(1));
    when(repository.findById(any())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class,() -> service.delete(1));
  }
}
