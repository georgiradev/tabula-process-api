package com.internship.tabulaprocessing.mapper;

import com.internship.tabulacore.dto.AccountDto;
import com.internship.tabulacore.entity.Account;
import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.*;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring")
public interface Mapper {

  Mapper INSTANCE = Mappers.getMapper(Mapper.class);

  Company companyRequestDtoToCompany(CompanyRequestDto companyRequestDto);

  CompanyResponseDto companyToCompanyResponseDto(Company company);

  DepartmentDTO convertToDepartmentDTO(Department department);

  Department convertToDepartmentEntity(DepartmentDTO departmentDTO);

  MediaDto convertToMediaDTO(Media media);

  Media convertToMediaEntity(MediaRequestDto mediaRequestDTO);

  MediaExtraDto convertToMediaExtraDTO(MediaExtra mediaExtra);

  MediaExtra convertToMediaExtraEntity(MediaExtraRequestDto mediaExtraRequestDto);

  List<MediaExtraDto> convertToMediaExtraDtoList(List<MediaExtra> medias);

  List<MediaDto> convertToMediaDtoList(List<Media> medias);

  @Mapping(source = "processEntity.id",target = "processId")
  @Mapping(source = "departmentEntity.id",target = "departmentId")
  @Mapping(source = "nextStageEntity.id",target = "nextStageId")
  ProcessStageResponseDTO convertToProcessStageDTO(ProcessStage processStage);

  ProcessStage convertToProcessStageEntity(ProcessStagePersistDTO processStagePersistDTO);

  ProcessStage convertToProcessStageEntity(ProcessStageResponseDTO processStageResponseDTO);

  ProcessResponseDto processToProcessGetDTO(Process process);

  Process processPostDTOtoProcess(ProcessRequestDto processRequestDto);

  Process processPutDTOtoProcess(ProcessRequestDto processRequestDto);

  @Mapping(target = "media.id", source = "mediaId")
  @Mapping(target = "order.id", source = "orderId")
  OrderItem orderItemRequestDtoToEntity(OrderItemRequestDto orderItemRequestDto);

  @Mapping(target = "media.id", source = "mediaId")
  OrderItem orderItemRequestDtoToEntity(OrderItemPersistRequestDto orderItemRequestDto);

  @Mapping(source = "media.id", target = "mediaId")
  @Mapping(source = "order.id", target = "orderId")
  @Mapping(source = "pricePerPiece", target = "totalPrice")
  OrderItemResponseDto orderItemDtoToEntity(OrderItem orderItem);

  @Mapping(source = "dateTimeCreated",target = "dateTimeCreated",dateFormat = "yyyy-MM-dd HH:mm:ss")
  @Mapping(source = "customer.id",target = "customerId")
  @Mapping(source = "processStage.id",target = "processStageId")
  @Mapping(source = "processStage",target = "processStage")
  OrderResponseDto orderToOrderResponseDto(Order order);


  Order convertToOrderEntity(OrderRequestDto orderRequestDto);

  Order convertToOrderEntity(OrderUpdateRequestDTO orderRequestDto);

  Employee convertToEmployeeEntity(EmployeeRequestDto employeeRequestDto);

  EmployeeResponseDto convertToEmployeeResponseDto(Employee employee);

  List<EmployeeResponseDto> convertToEmployeeResponseDtoList(List<Employee> employees);

  List<CustomerResponseDto> convertToCustomerDto(List<Customer> customers);

  AccountDto convertToAccountDto(Account account);

  @Mapping(source = "companyId", target = "company.id")
  Customer customerDtoToEntity(CustomerRequestDto customerRequestDto);

  @Mapping(source = "customer.company.id", target = "companyId")
  CustomerResponseDto customerEntityToDto(Customer customer);

  @Mapping(source = "paid", target = "paid")
  TimeOffType timeOffTypeRequestDtoToEntity(TimeOffTypeRequestDto timeOffTypeRequestDto);

  @Mapping(source = "paid", target = "paid")
  TimeOffTypeResponseDto entityToTimeOffTypeResponseDto(TimeOffType timeOffType);

  CustomerDtoNoCompany customerEntityToCustomerDto(Customer currentCustomer);
}



