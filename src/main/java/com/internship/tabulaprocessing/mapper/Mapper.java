package com.internship.tabulaprocessing.mapper;

import com.internship.tabulacore.dto.AccountDto;
import com.internship.tabulacore.entity.Account;
import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.service.EmployeeService;
import com.internship.tabulaprocessing.service.TimeOffTypeService;
import com.internship.tabulaprocessing.entity.*;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.mapstruct.Mapper(
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class Mapper {

  @Autowired
  EmployeeService employeeService;

  @Autowired
  TimeOffTypeService timeOffTypeService;

  public abstract Company companyRequestDtoToCompany(CompanyRequestDto companyRequestDto);

  @Mapping(target = "customers", ignore = true)
  public abstract CompanyResponseDto companyToCompanyResponseDto(Company company);

  public abstract DepartmentDTO convertToDepartmentDTO(Department department);

  public abstract Department convertToDepartmentEntity(DepartmentDTO departmentDTO);

  public abstract MediaDto convertToMediaDTO(Media media);

  public abstract Media convertToMediaEntity(MediaDto mediaDTO);

  public abstract MediaExtraDto convertToMediaExtraDTO(MediaExtra mediaExtra);

  public abstract MediaExtra convertToMediaExtraEntity(MediaExtraDto mediaExtraDto);

  public abstract List<MediaExtraDto> convertToMediaExtraDtoList (List<MediaExtra> medias);

  public abstract List<MediaDto> convertToMediaDtoList (List<Media> medias);

  @Mapping(source = "processEntity.id",target = "processId")
  @Mapping(source = "departmentEntity.id",target = "departmentId")
  @Mapping(source = "nextStageEntity.id",target = "nextStageId")
  public abstract ProcessStageResponseDTO convertToProcessStageDTO(ProcessStage processStage);

  public abstract ProcessStage convertToProcessStageEntity(ProcessStagePersistDTO processStagePersistDTO);

  public abstract ProcessStage convertToProcessStageEntity(ProcessStageResponseDTO processStageResponseDTO);

  public abstract ProcessResponseDto processToProcessGetDTO(Process process);

  public abstract Process processPostDTOtoProcess(ProcessRequestDto processRequestDto);

  public abstract Process processPutDTOtoProcess(ProcessRequestDto processRequestDto);

  @Mapping(target = "media.id", source = "mediaId")
  @Mapping(target = "order.id", source = "orderId")
  public abstract OrderItem orderItemRequestDtoToEntity(OrderItemRequestDto orderItemRequestDto);

  @Mapping(target = "media.id", source = "mediaId")
  public abstract OrderItem orderItemRequestDtoToEntity(OrderItemPersistRequestDto orderItemRequestDto);

  @Mapping(source = "media.id", target = "mediaId")
  @Mapping(source = "order.id", target = "orderId")
  @Mapping(source = "pricePerPiece", target = "totalPrice")
  public abstract OrderItemResponseDto orderItemDtoToEntity(OrderItem orderItem);

  public abstract Order orderRequestDtoToOrder(OrderRequestDto orderRequestDto);

  @Mapping(source = "dateTimeCreated",target = "dateTimeCreated",dateFormat = "yyyy-MM-dd HH:mm:ss")
  @Mapping(source = "customer.id",target = "customerId")
  @Mapping(source = "processStage.id",target = "processStageId")
  @Mapping(source = "processStage",target = "processStage")
  public abstract OrderResponseDto orderToOrderResponseDto(Order order);

  public abstract Employee convertToEmployeeEntity(EmployeeRequestDto employeeRequestDto);

  public abstract Order convertToOrderEntity(OrderRequestDto orderRequestDto);

  public abstract Order convertToOrderEntity(OrderUpdateRequestDTO orderRequestDto);

  @Mapping(target = "account", source = "account")
  public abstract Employee convertToEmployeeEntity(EmployeeResponseDto employeeResponseDto);

  public abstract EmployeeResponseDto convertToEmployeeResponseDto(Employee employee);

  public abstract List<EmployeeResponseDto> convertToEmployeeResponseDtoList(List<Employee> employees);

  public abstract List<CustomerResponseDto> convertToCustomerDto(List<Customer> customers);

  public abstract AccountDto convertToAccountDto(Account account);

  @Mapping(source = "companyId", target = "company.id")
  public abstract Customer customerDtoToEntity(CustomerRequestDto customerRequestDto);

  @Mapping(source = "customer.company.id", target = "companyId")
  public abstract CustomerResponseDto customerEntityToDto(Customer customer);

  public abstract CustomerDtoNoCompany customerEntityToCustomerDto(Customer customer);
  
  @Mapping(source = "paid", target = "paid")
  public abstract TimeOffType timeOffTypeRequestDtoToEntity(TimeOffTypeRequestDto timeOffTypeRequestDto);

  @Mapping(source = "paid", target = "paid")
  public abstract TimeOffTypeResponseDto entityToTimeOffTypeResponseDto(TimeOffType timeOffType);

  public abstract List<TimeOffResponse> convertToTimeOffResponse (List<TimeOff> timeOffs);

  @Mapping(target = "typeOfTimeOffId", source = "timeOffType.id")
  @Mapping(target = "employeeId", expression = "java(timeOff.getEmployee().getId())")
  @Mapping(target = "approverId", expression = "java(timeOff.getApprover().getId())")
  public abstract TimeOffResponse convertToTimeOffResponse(TimeOff timeOff) ;

  public TimeOff convertToTimeOffEntity (TimeOffRequest dto) {
    if ( dto == null ) {
      return null;
    }

    Employee employee;
    Employee approver;

    EmployeeResponseDto employeeResponseDto = employeeService.getOne(dto.getEmployeeId()).getBody();
    employee = convertToEmployeeEntity(employeeResponseDto);

    EmployeeResponseDto approverResponseDto = employeeService.getOne(dto.getApproverId()).getBody();
    approver = convertToEmployeeEntity(approverResponseDto);

    TimeOff timeOff = new TimeOff();

    timeOff.setStartDateTime( dto.getStartDateTime() );
    timeOff.setEndDateTime( dto.getEndDateTime() );
    timeOff.setComment( dto.getComment() );
    timeOff.setTimeOffType(timeOffTypeService.getOneById(dto.getTypeOfTimeOffId()));
    timeOff.setEmployee(employee);
    timeOff.setApprover(approver);

    return timeOff;
  }
}



