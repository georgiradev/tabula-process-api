package com.internship.tabulaprocessing.mapper;

import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.Company;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.dto.DepartmentDTO;
import com.internship.tabulaprocessing.dto.CompanyRequestDto;
import com.internship.tabulaprocessing.dto.CompanyRequestPatchDto;
import com.internship.tabulaprocessing.dto.CompanyResponseDto;
import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.entity.Media;
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

    Media convertToMediaEntity(MediaDto mediaDTO);

}
