package com.internship.tabulaprocessing.mapper;

import com.internship.tabulacore.dto.AccountDto;
import com.internship.tabulacore.entity.Account;
import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.service.CustomerService;
import com.internship.tabulaprocessing.service.EmployeeService;
import com.internship.tabulaprocessing.service.TimeOffTypeService;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.mapstruct.Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring")
public abstract class Mapper {

  @Autowired private EmployeeService employeeService;

  @Autowired private CustomerService customerService;

  @Autowired private TimeOffTypeService timeOffTypeService;

  public abstract Company companyRequestDtoToCompany(CompanyRequestDto companyRequestDto);

  public abstract DepartmentDTO convertToDepartmentDTO(Department department);

  public abstract Department convertToDepartmentEntity(DepartmentDTO departmentDTO);

  public abstract MediaDto convertToMediaDTO(Media media);

  public abstract Media convertToMediaEntity(MediaRequestDto mediaRequestDTO);

  public abstract MediaExtraDto convertToMediaExtraDTO(MediaExtra mediaExtra);

  public abstract MediaExtra convertToMediaExtraEntity(MediaExtraRequestDto mediaExtraRequestDto);

  public abstract List<MediaExtraDto> convertToMediaExtraDtoList(List<MediaExtra> medias);

  public abstract List<MediaDto> convertToMediaDtoList(List<Media> medias);

  @Mapping(source = "processEntity.id", target = "processId")
  @Mapping(source = "departmentEntity.id", target = "departmentId")
  @Mapping(source = "nextStageEntity.id", target = "nextStageId")
  public abstract ProcessStageResponseDTO convertToProcessStageDTO(ProcessStage processStage);

  public abstract ProcessStage convertToProcessStageEntity(
      ProcessStagePersistDTO processStagePersistDTO);

  public abstract ProcessStage convertToProcessStageEntity(
      ProcessStageResponseDTO processStageResponseDTO);

  public abstract ProcessResponseDto processToProcessGetDTO(Process process);

  public abstract Process processPostDTOtoProcess(ProcessRequestDto processRequestDto);

  public abstract Process processPutDTOtoProcess(ProcessRequestDto processRequestDto);

  @Mapping(target = "media.id", source = "mediaId")
  public abstract OrderItem orderItemRequestDtoToEntity(
      OrderItemPersistRequestDto orderItemRequestDto);

  @Mapping(source = "media.id", target = "mediaId")
  @Mapping(source = "order.id", target = "orderId")
  @Mapping(source = "pricePerPiece", target = "totalPrice")
  public abstract OrderItemResponseDto orderItemDtoToEntity(OrderItem orderItem);

  @Mapping(
      source = "dateTimeCreated",
      target = "dateTimeCreated",
      dateFormat = "yyyy-MM-dd HH:mm:ss")
  @Mapping(source = "customer.id", target = "customerId")
  @Mapping(source = "processStage.id", target = "processStageId")
  @Mapping(source = "processStage", target = "processStage")
  public abstract OrderResponseDto orderToOrderResponseDto(Order order);

  public abstract Order convertToOrderEntity(OrderRequestDto orderRequestDto);

  public abstract Order convertToOrderEntity(OrderUpdateRequestDTO orderRequestDto);

  public abstract Employee convertToEmployeeEntity(EmployeeRequestDto employeeRequestDto);

  public abstract EmployeeResponseDto convertToEmployeeResponseDto(Employee employee);

  @Mapping(target = "account", source = "account")
  public abstract Employee convertToEmployeeEntity(EmployeeResponseDto employeeResponseDto);

  public abstract List<EmployeeResponseDto> convertToEmployeeResponseDtoList(
      List<Employee> employees);

  public abstract List<CustomerResponseDto> convertToCustomerDto(List<Customer> customers);

  public abstract AccountDto convertToAccountDto(Account account);

  @Mapping(source = "companyId", target = "company.id")
  public abstract Customer customerDtoToEntity(CustomerRequestDto customerRequestDto);

  public CustomerResponseDto customerEntityToDto(Customer customer) {
    Account account = customerService.getAccount(customer.getAccountId());
    CustomerResponseDto customerResponseDto = new CustomerResponseDto();

    customerResponseDto.setId(customer.getId());
    customerResponseDto.setCompany(companyToDto(customer.getCompany()));
    customerResponseDto.setCompanyId(customer.getCompany().getId());
    customerResponseDto.setAccountId(customer.getAccountId());
    customerResponseDto.setPhone(customer.getPhone());
    customerResponseDto.setAccount(convertToAccountDto(account));
    customerResponseDto.setOrdersIds(customerService.getOrdersIds(customerResponseDto.getId()));

    return customerResponseDto;
  }

  public abstract CompanyDtoNoCustomers companyToDto(Company company);

  @Mapping(source = "isPaid", target = "isPaid")
  public abstract TimeOffType timeOffTypeRequestDtoToEntity(
      TimeOffTypeRequestDto timeOffTypeRequestDto);

  public abstract CustomerDtoNoCompany customerEntityToCustomerDto(Customer currentCustomer);

  @Mapping(source = "order.id", target = "orderId")
  @Mapping(source = "assignee.id", target = "assigneeId")
  @Mapping(
      source = "dateTimeUpdated",
      target = "dateTimeUpdated",
      dateFormat = "yyyy-MM-dd HH-mm-ss")
  @Mapping(source = "processStage.id", target = "processStageId")
  public abstract TrackingHistoryResponseDTO convertToTrackingHistoryDTO(
      TrackingHistory trackingHistory);

  public abstract TrackingHistory convertToTrackingHistoryEntity(
      TrackingHistoryRequestDTO requestDTO);

  public abstract Media convertToMediaEntity(MediaDto foundMedia);

  public CompanyResponseDto convertToDto(Company company) {
    CompanyResponseDto companyResponseDto = new CompanyResponseDto();
    companyResponseDto.setId(company.getId());
    companyResponseDto.setVatNumber(company.getVatNumber());
    companyResponseDto.setName(company.getName());
    companyResponseDto.setDiscountRate(company.getDiscountRate());
    companyResponseDto.setCountry(company.getCountry());
    companyResponseDto.setCity(company.getCity());
    companyResponseDto.setAddress(company.getAddress());

    for (Customer currentCustomer : company.getCustomers()) {
      CustomerDtoNoCompany customerDto = customerEntityToCustomerDto(currentCustomer);
      Account account = customerService.getAccount(currentCustomer.getAccountId());
      customerDto.setOrdersIds(customerService.getOrdersIds(currentCustomer.getId()));
      customerDto.setAccount(convertToAccountDto(account));
      companyResponseDto.getCustomers().add(customerDto);
    }

    return companyResponseDto;
  }

  @Mapping(source = "isPaid", target = "isPaid")
  public abstract TimeOffTypeResponseDto entityToTimeOffTypeResponseDto(TimeOffType timeOffType);

  public abstract List<TimeOffResponse> convertToTimeOffResponse(List<TimeOff> timeOffs);

  @Mapping(target = "typeOfTimeOffId", source = "timeOffType.id")
  @Mapping(target = "employeeId", expression = "java(timeOff.getEmployee().getId())")
  @Mapping(target = "approverId", expression = "java(timeOff.getApprover().getId())")
  public abstract TimeOffResponse convertToTimeOffResponse(TimeOff timeOff);

  public TimeOff convertToTimeOffEntity(TimeOffRequest dto) {
    if (dto == null) {
      return null;
    }

    Employee employee;
    Employee approver;

    EmployeeResponseDto employeeResponseDto = employeeService.getOne(dto.getEmployeeId());
    employee = convertToEmployeeEntity(employeeResponseDto);

    EmployeeResponseDto approverResponseDto = employeeService.getOne(dto.getApproverId());
    approver = convertToEmployeeEntity(approverResponseDto);

    TimeOff timeOff = new TimeOff();

    timeOff.setStartDateTime(dto.getStartDateTime());
    timeOff.setEndDateTime(dto.getEndDateTime());
    timeOff.setComment(dto.getComment());
    timeOff.setTimeOffType(timeOffTypeService.getOneById(dto.getTypeOfTimeOffId()));
    timeOff.setEmployee(employee);
    timeOff.setApprover(approver);

    return timeOff;
  }
}
