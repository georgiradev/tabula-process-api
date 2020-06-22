package com.internship.tabulaprocessing.mapper;

import com.internship.tabulacore.dto.AccountDto;
import com.internship.tabulacore.entity.Account;
import com.internship.tabulaprocessing.dto.*;

import com.internship.tabulaprocessing.entity.*;

import com.internship.tabulaprocessing.entity.Process;
<<<<<<< HEAD
=======
import com.internship.tabulaprocessing.entity.*;
>>>>>>> develop
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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

  ProcessStageResponseDTO convertToProcessStageDTO(ProcessStage processStage);

  ProcessStage convertToProcessStageEntity(ProcessStagePersistDTO processStagePersistDTO);
<<<<<<< HEAD

  ProcessStage convertToProcessStageEntity(ProcessStageResponseDTO processStageResponseDTO);

  ProcessResponseDto processToProcessGetDTO(Process process);

  Process processPostDTOtoProcess(ProcessRequestDto processRequestDto);

  Process processPutDTOtoProcess(ProcessRequestDto processRequestDto);

  @Mapping(target = "media.id", source = "mediaId")
  @Mapping(target = "order.id", source = "orderId")
  OrderItem orderItemRequestDtoToEntity(OrderItemRequestDto orderItemRequestDto);

  @Mapping(source = "media.id", target = "mediaId")
  @Mapping(source = "order.id", target = "orderId")
  OrderItemResponseDto orderItemDtoToEntity(OrderItem orderItem);

  Order orderRequestDtoToOrder(OrderRequestDto orderRequestDto);

  OrderResponseDto orderToOrderResponseDto(Order order);

    Employee convertToEmployeeEntity(EmployeeRequestDto employeeRequestDto);

    EmployeeResponseDto convertToEmployeeResponseDto(Employee employee);

    AccountDto convertToAccountDto(Account account);
=======

  ProcessStage convertToProcessStageEntity(ProcessStageResponseDTO processStageResponseDTO);

  ProcessResponseDto processToProcessGetDTO(Process process);

  Process processPostDTOtoProcess(ProcessRequestDto processRequestDto);

  Process processPutDTOtoProcess(ProcessRequestDto processRequestDto);

  @Mapping(target = "media.id", source = "mediaId")
  @Mapping(target = "order.id", source = "orderId")
  OrderItem orderItemRequestDtoToEntity(OrderItemRequestDto orderItemRequestDto);

  @Mapping(source = "media.id", target = "mediaId")
  @Mapping(source = "order.id", target = "orderId")
  OrderItemResponseDto orderItemDtoToEntity(OrderItem orderItem);

  Order orderRequestDtoToOrder(OrderRequestDto orderRequestDto);

  OrderResponseDto orderToOrderResponseDto(Order order);
>>>>>>> develop
}



