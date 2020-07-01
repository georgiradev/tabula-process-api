package com.internship.tabulaprocessing.mapper;

import com.internship.tabulacore.dto.AccountDto;
import com.internship.tabulacore.entity.Account;
import com.internship.tabulaprocessing.dto.*;

import com.internship.tabulaprocessing.entity.*;

import com.internship.tabulaprocessing.entity.Process;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@org.mapstruct.Mapper(
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface Mapper {

  Mapper INSTANCE = Mappers.getMapper(Mapper.class);

  Company companyRequestDtoToCompany(CompanyRequestDto companyRequestDto);

  @Mapping(target = "customers", ignore = true)
  CompanyResponseDto companyToCompanyResponseDto(Company company);

  DepartmentDTO convertToDepartmentDTO(Department department);

  Department convertToDepartmentEntity(DepartmentDTO departmentDTO);

  MediaDto convertToMediaDTO(Media media);

  Media convertToMediaEntity(MediaRequestDto mediaRequestDTO);

  MediaExtraDto convertToMediaExtraDTO(MediaExtra mediaExtra);

  MediaExtra convertToMediaExtraEntity(MediaExtraRequestDto mediaExtraRequestDto);

  List<MediaExtraDto> convertToMediaExtraDtoList (List<MediaExtra> medias);

  List<MediaDto> convertToMediaDtoList (List<Media> medias);

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

  EmployeeResponseDto convertToEmployeeResponseDto(Employee employee);
  
  List<EmployeeResponseDto> convertToEmployeeResponseDtoList(List<Employee> employees);

  List<CustomerResponseDto> convertToCustomerDto(List<Customer> customers);

  AccountDto convertToAccountDto(Account account);

  @Mapping(source = "companyId", target = "company.id")
  Customer customerDtoToEntity(CustomerRequestDto customerRequestDto);

  @Mapping(source = "customer.company.id", target = "companyId")
  CustomerResponseDto customerEntityToDto(Customer customer);

  CustomerDtoNoCompany customerEntityToCustomerDto(Customer customer);
  
  @Mapping(source = "paid", target = "paid")
  TimeOffType timeOffTypeRequestDtoToEntity(TimeOffTypeRequestDto timeOffTypeRequestDto);

  @Mapping(source = "paid", target = "paid")
  TimeOffTypeResponseDto entityToTimeOffTypeResponseDto(TimeOffType timeOffType);
}



