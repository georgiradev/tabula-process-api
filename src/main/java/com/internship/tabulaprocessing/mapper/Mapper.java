package com.internship.tabulaprocessing.mapper;

import com.internship.tabulaprocessing.dto.CompanyRequestDto;
import com.internship.tabulaprocessing.dto.CompanyRequestPatchDto;
import com.internship.tabulaprocessing.dto.CompanyResponseDto;
import com.internship.tabulaprocessing.entity.Company;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring")
public interface Mapper {

  Mapper INSTANCE = Mappers.getMapper(Mapper.class);

  Company companyRequestDtoToCompany(CompanyRequestDto companyRequestDto);

  CompanyResponseDto companyToCompanyResponseDto(Company company);

  Company companyRequestDtoToCompany(CompanyRequestPatchDto companyRequestDto);
}
