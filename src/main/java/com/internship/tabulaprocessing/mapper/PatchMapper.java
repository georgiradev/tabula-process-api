package com.internship.tabulaprocessing.mapper;


import com.internship.tabulaprocessing.dto.OrderPatchRequestDTO;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.dto.MediaExtraRequestDto;
import com.internship.tabulaprocessing.dto.MediaRequestDto;
import com.internship.tabulaprocessing.dto.OrderPatchRequestDTO;
import com.internship.tabulaprocessing.dto.TrackingHistoryRequestDTO;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.dto.TimeOffTypeRequestDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.TimeOffType;
import com.internship.tabulaprocessing.entity.TrackingHistory;
import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.service.*;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@org.mapstruct.Mapper
        (componentModel = "spring",
                uses = {EmployeeService.class, Mapper.class, OrderService.class, DepartmentService.class, Mapper.class, MediaService.class, CompanyService.class},
                nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PatchMapper {

    @Autowired
    private OrderService orderService;

    @Autowired private MediaService mediaService;

    @Autowired private CompanyService companyService;

    @Autowired private DepartmentService departmentService;

    @Autowired private Mapper mapper;

    @Autowired EmployeeService employeeService;

    @Autowired TimeOffTypeService timeOffTypeService;

    Order patchOrder(OrderPatchRequestDTO dto, @MappingTarget Order order);

  @Autowired TimeOffTypeService timeOffTypeService;

    public abstract Order patchOrder(OrderPatchRequestDTO dto, @MappingTarget Order order);

    public abstract TimeOffType mapObjectsToTimeOffType(
            TimeOffTypeRequestDto data, @MappingTarget TimeOffType timeOffType);

    public abstract Media mapObjectsToMedia(MediaRequestDto data, @MappingTarget Media media);

    public abstract MediaExtra mapObjectsToMediaExtra(
            MediaExtraRequestDto data, @MappingTarget MediaExtra mediaExtra);

    public abstract Company mapObjectsToCompany(
            CompanyPatchDto companyPatchDto, @MappingTarget Company company);

    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "mediaId", target = "media.id")
    public OrderItem mapObjectsToOrderItem(
            OrderItemPatchDto orderItemPatchDto, @MappingTarget OrderItem currentOrderItem) {
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

    @Mapping(source = "companyId", target = "company.id")
    public Customer mapObjectsToCustomer(
            CustomerPatchDto customerPatchDto, @MappingTarget Customer currentCustomer) {

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
          mapper.convertToEmployeeEntity(employeeService.getOne(data.getApproverId()).getBody());
      timeOff.setApprover(approver);
    }

    if (data.getEmployeeId() != 0) {
      Employee employee =
          mapper.convertToEmployeeEntity(employeeService.getOne(data.getEmployeeId()).getBody());
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

  public abstract Process mapObjectsToProcess(
      ProcessRequestDto data, @MappingTarget Process process);

  public abstract TrackingHistory patchTrackingHistory(
      TrackingHistoryRequestDTO requestDTO, @MappingTarget TrackingHistory trackingHistory);
}
