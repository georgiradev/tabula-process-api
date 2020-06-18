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

  DepartmentDTO coventToDepartmentDTO(Department department);

  Department convertToDepartmentEntity(DepartmentDTO departmentDTO);
  
  ProcessResponseDto processToProcessGetDTO(Process process);
  
  Process processPostDTOtoProcess(ProcessRequestDto processPostDTO);
  
  Process processPutDTOtoProcess(ProcessRequestDto processRequestDto);

  MediaDto convertToMediaDTO(Media media);

  Media convertToMediaEntity(MediaDto mediaDto);

  MediaExtraDto convertToMediaExtraDTO(MediaExtra mediaExtra);

  MediaExtra convertToMediaExtraEntity(MediaExtraDto mediaExtraDto);

  EmployeeDto convertToEmployeeDTO(Employee employee);

  Employee convertToEmployeeEntity(EmployeeDto employeeDto);

  AccountDto convertToAccountDto(Account account);


}
