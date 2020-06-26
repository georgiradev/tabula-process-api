package com.internship.tabulaprocessing.mapper;

import com.internship.tabulacore.dto.AccountDto;
import com.internship.tabulacore.entity.Account;
import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.*;
import org.mapstruct.Mapping;
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

  Media convertToMediaEntity(MediaDto mediaDTO);

  MediaExtraDto convertToMediaExtraDTO(MediaExtra mediaExtra);

  MediaExtra convertToMediaExtraEntity(MediaExtraDto mediaExtraDto);

  List<MediaExtraDto> convertToMediaExtraDtoList(List<MediaExtra> medias);

  List<MediaDto> convertToMediaDtoList(List<Media> medias);

  ProcessStageResponseDTO convertToProcessStageDTO(ProcessStage processStage);

  ProcessStage convertToProcessStageEntity(ProcessStagePersistDTO processStagePersistDTO);

  ProcessStage convertToProcessStageEntity(ProcessStageResponseDTO processStageResponseDTO);

  ProcessResponseDto processToProcessGetDTO(Process process);

  Process processPostDTOtoProcess(ProcessRequestDto processRequestDto);

  Process processPutDTOtoProcess(ProcessRequestDto processRequestDto);

  @Mapping(target = "media.id", source = "mediaId")
  @Mapping(target = "order.id", source = "orderId")
  OrderItem orderItemRequestDtoToEntity(OrderItemRequestDto orderItemRequestDto);

  @Mapping(source = "media.id", target = "mediaId")
  @Mapping(source = "order.id", target = "orderId")
  @Mapping(source = "pricePerPiece", target = "totalPrice")
  OrderItemResponseDto orderItemDtoToEntity(OrderItem orderItem);

  Order orderRequestDtoToOrder(OrderRequestDto orderRequestDto);

  OrderResponseDto orderToOrderResponseDto(Order order);

  Employee convertToEmployeeEntity(EmployeeRequestDto employeeRequestDto);

  @Mapping(target = "account", source = "account")
  Employee convertToEmployeeEntity(EmployeeResponseDto employeeResponseDto);

  EmployeeResponseDto convertToEmployeeResponseDto(Employee employee);

  List<EmployeeResponseDto> convertToEmployeeResponseDtoList(List<Employee> employees);

  AccountDto convertToAccountDto(Account account);

  @Mapping(source = "companyId", target = "company.id")
  Customer customerDtoToEntity(CustomerRequestDto customerRequestDto);

  CustomerResponseDto customerEntityToDto(Customer customer);

  @Mapping(source = "paid", target = "paid")
  TimeOffType timeOffTypeRequestDtoToEntity(TimeOffTypeRequestDto timeOffTypeRequestDto);

  @Mapping(source = "paid", target = "paid")
  TimeOffTypeResponseDto entityToTimeOffTypeResponseDto(TimeOffType timeOffType);

  TimeOff convertToTimeOffEntity (TimeOffRequest dto);

  @Mapping(target = "typeOfTimeOff", source = "timeOffType.name")
  @Mapping(target = "employeeId", expression = "java(timeOff.getEmployee().getId())")
  @Mapping(target = "approverId", expression = "java(timeOff.getApprover().getId())")
  TimeOffResponse convertToTimeOffResponse(TimeOff timeOff);
}
