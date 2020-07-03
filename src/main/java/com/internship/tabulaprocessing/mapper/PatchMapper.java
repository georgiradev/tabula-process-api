package com.internship.tabulaprocessing.mapper;

import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.service.*;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@org.mapstruct.Mapper(
    componentModel = "spring",
    uses = {
      EmployeeService.class,
      Mapper.class,
      OrderService.class,
      DepartmentService.class,
      Mapper.class,
      MediaService.class,
      CompanyService.class
    },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PatchMapper {

  @Autowired
  private OrderService orderService;

  @Autowired
  private MediaService mediaService;

  @Autowired
  private CompanyService companyService;

  @Autowired
  private DepartmentService departmentService;

  @Autowired
  private Mapper mapper;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private TrackingHistoryService trackingHistoryService;

  @Autowired private TimeOffTypeService timeOffTypeService;

  public abstract Company mapObjectsToCompany(
      CompanyPatchDto companyPatchDto, @MappingTarget Company company);

  public OrderItem mapObjectsToOrderItem(OrderItemPatchDto orderItemPatchDto,
                                            OrderItem currentOrderItem) {
    OrderItem patchedOrderItem = new OrderItem();

    if (orderItemPatchDto == null) {
      return currentOrderItem;
    }

    if (orderItemPatchDto.getHeight() != null) {
      patchedOrderItem.setHeight(orderItemPatchDto.getHeight());
    } else {
      patchedOrderItem.setHeight(currentOrderItem.getHeight());
    }

    if (orderItemPatchDto.getWidth() != null) {
      patchedOrderItem.setWidth(orderItemPatchDto.getWidth());
    } else {
      patchedOrderItem.setWidth(currentOrderItem.getWidth());
    }

    if (orderItemPatchDto.getCount() != null) {
      patchedOrderItem.setCount(orderItemPatchDto.getCount());
    } else {
      patchedOrderItem.setCount(currentOrderItem.getCount());
    }

    patchedOrderItem.setPricePerPiece(currentOrderItem.getPricePerPiece());

    if (orderItemPatchDto.getNote() != null) {
      patchedOrderItem.setNote(orderItemPatchDto.getNote());
    } else {
      patchedOrderItem.setNote(currentOrderItem.getNote());
    }

    if (orderItemPatchDto.getMediaId() != null) {
      MediaDto foundMedia = mediaService.getOne(orderItemPatchDto.getMediaId());
      patchedOrderItem.setMedia(mapper.convertToMediaEntity(foundMedia));
    } else {
      patchedOrderItem.setMedia(currentOrderItem.getMedia());
    }

    if (orderItemPatchDto.getOrderId() != null) {
      Order foundOrder = orderService.getOneById(orderItemPatchDto.getOrderId());
      patchedOrderItem.setOrder(foundOrder);
    } else {
      patchedOrderItem.setOrder(currentOrderItem.getOrder());
    }

    return patchedOrderItem;
  }

  public Customer mapObjectsToCustomer(CustomerPatchDto customerPatchDto,
                                       Customer currentCustomer) {

    if (customerPatchDto == null) {
      return currentCustomer;
    }

    Customer patchedCustomer = new Customer();
    patchedCustomer.setId(currentCustomer.getId());

    if (customerPatchDto.getAccountId() != null) {
      patchedCustomer.setAccountId(customerPatchDto.getAccountId());
    } else {
      patchedCustomer.setAccountId(currentCustomer.getAccountId());
    }

    if (customerPatchDto.getCompanyId() != null) {
      Company foundCompany = companyService.find(customerPatchDto.getCompanyId());
      patchedCustomer.setCompany(foundCompany);
    } else {
      patchedCustomer.setCompany(currentCustomer.getCompany());
    }

    if (customerPatchDto.getPhone() != null) {
      patchedCustomer.setPhone(customerPatchDto.getPhone());
    } else {
      patchedCustomer.setPhone(currentCustomer.getPhone());
    }
    patchedCustomer.setOrders(currentCustomer.getOrders());

    return patchedCustomer;
  }

  public abstract Process mapObjectsToProcess(
      ProcessRequestDto data, @MappingTarget Process process);

  public abstract TrackingHistory patchTrackingHistory(
      TrackingHistoryRequestDTO requestDTO, @MappingTarget TrackingHistory trackingHistory);

  public Employee patchEmployee(EmployeeUpdateRequestDto data, Employee employee) {
    if (data == null) {
      return null;
    }

    if (data.getRatePerHour() != null) {
      employee.setRatePerHour(data.getRatePerHour());
    }

    if (data.getDepartmentId() != 0) {
      Department department = departmentService.findById(data.getDepartmentId());
      employee.setDepartment(department);
    }
    return employee;
  }

  public abstract TimeOffType mapObjectsToTimeOffType(
      TimeOffTypeRequestDto data, @MappingTarget TimeOffType timeOffType);

  public abstract TimeOff mapObjectsToTimeOffEntity(
      TimeOffPatchStatusRequest data, @MappingTarget TimeOff timeOff);

  public TimeOff mapObjectsToTimeOffEntity(TimeOffPatchRequest data, TimeOff timeOff) {
    if (data == null) {
      return null;
    }

    if (data.getStartDateTime() != null) {
      timeOff.setStartDateTime(data.getStartDateTime());
    }

    if (data.getEndDateTime() != null) {
      timeOff.setEndDateTime(data.getEndDateTime());
    }

    if (data.getComment() != null) {
      timeOff.setComment(data.getComment());
    }

    if (data.getApproverId() != 0) {
      Employee approver =
          mapper.convertToEmployeeEntity(employeeService.getOne(data.getApproverId()));
      timeOff.setApprover(approver);
    }

    if (data.getEmployeeId() != 0) {
      Employee employee =
          mapper.convertToEmployeeEntity(employeeService.getOne(data.getEmployeeId()));
      timeOff.setEmployee(employee);
    }

    if (data.getTypeOfTimeOffId() != 0) {
      TimeOffType timeOffType = timeOffTypeService.getOneById(data.getTypeOfTimeOffId());
      timeOff.setTimeOffType(timeOffType);
    }

    return timeOff;
  }

  public abstract Media mapObjectsToMedia(MediaRequestDto data, @MappingTarget Media media);

  public abstract MediaExtra mapObjectsToMediaExtra(
      MediaExtraRequestDto data, @MappingTarget MediaExtra mediaExtra);

  public abstract Order patchOrder(OrderPatchRequestDTO dto, @MappingTarget Order order);
      
  public WorkLog mapObjectsToWorkLog (WorkLogPatchRequest data, WorkLog workLog) {
    if ( data == null ) {
      return null;
    }

    if( data.getHoursSpent() != 0 ) {
      double totalHoursSpent = data.getHoursSpent() + workLog.getHoursSpent();
      workLog.setHoursSpent( totalHoursSpent );
    }

    if( data.getEmployeeId() != 0 ) {
      workLog.setEmployee(mapper.convertToEmployeeEntity(employeeService
              .getOne(data.getEmployeeId())));
    }

    if( data.getTrackingHistoryId() != 0 ) {
      workLog.setTrackingHistory(trackingHistoryService.findById(data.getTrackingHistoryId()));
    }

    workLog.setUpdatedDateTime(LocalDateTime.now());

    return workLog;
  }
}
