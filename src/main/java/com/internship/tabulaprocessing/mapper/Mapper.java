package com.internship.tabulaprocessing.mapper;

import com.internship.tabulacore.dto.AccountDto;
import com.internship.tabulacore.entity.Account;
import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.entity.Process;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring")
public interface Mapper {

  Mapper INSTANCE = Mappers.getMapper(Mapper.class);

  Company companyRequestDtoToCompany(CompanyRequestDto companyRequestDto);

  CompanyResponseDto companyToCompanyResponseDto(Company company);

  Company companyRequestDtoToCompany(CompanyRequestPatchDto companyRequestDto);

  DepartmentDTO convertToDepartmentDTO(Department department);

  Department convertToDepartmentEntity(DepartmentDTO departmentDTO);

  MediaDto convertToMediaDTO(Media media);

  Media convertToMediaEntity(MediaDto mediaDto);

  MediaExtraDto convertToMediaExtraDTO(MediaExtra mediaExtra);

  MediaExtra convertToMediaExtraEntity(MediaExtraDto mediaExtraDto);

    ProcessStageResponseDTO convertToProcessStageDTO(ProcessStage processStage);

    ProcessStage convertToProcessStageEntity(ProcessStagePersistDTO processStagePersistDTO);

    ProcessStage convertToProcessStageEntity(ProcessStageResponseDTO processStageResponseDTO);

    ProcessResponseDto processToProcessGetDTO(Process process);

    Process processPostDTOtoProcess(ProcessRequestDto processRequestDto);

    Process processPutDTOtoProcess(ProcessRequestDto processRequestDto);
    
    Employee convertToEmployeeEntity(EmployeeRequestDto employeeRequestDto);

    EmployeeResponseDto convertToEmployeeResponseDto(Employee employee);

    AccountDto convertToAccountDto(Account account);
}
