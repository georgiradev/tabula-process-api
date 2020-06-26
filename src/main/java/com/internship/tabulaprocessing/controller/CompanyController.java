package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.CompanyRequestDto;
import com.internship.tabulaprocessing.dto.CompanyResponseDto;
import com.internship.tabulaprocessing.entity.Company;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
  private final CompanyService companyService;
  private final Mapper mapper;

  @PostMapping
  public ResponseEntity<CompanyResponseDto> createCompany(
      @Valid @RequestBody CompanyRequestDto companyRequestDto) {

    Company company = mapper.companyRequestDtoToCompany(companyRequestDto);
    Optional<Company> savedCompany = companyService.save(company);

    if (savedCompany.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    CompanyResponseDto companyResponseDto = convertToDto(savedCompany.get());

    return ResponseEntity.ok(companyResponseDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CompanyResponseDto> getCompany(@PathVariable("id") @Min(1) int id) {
    Optional<Company> foundCompany = companyService.find(id);

    if (foundCompany.isPresent()) {
      CompanyResponseDto companyResponseDto = convertToDto(foundCompany.get());

      return ResponseEntity.ok(companyResponseDto);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @GetMapping
  public ResponseEntity<PagedResult<CompanyResponseDto>> getAllCompanies(
      @Valid QueryParameter queryParameter) {

    Page<Company> pagedResult = companyService.findAll(queryParameter);
    Page<CompanyResponseDto> pagedResultDto = pagedResult.map(this::convertToDto);

    PagedResult<CompanyResponseDto> allToDto =
        new PagedResult<>(
            pagedResultDto.toList(), queryParameter.getPage(), pagedResultDto.getTotalPages());

    return ResponseEntity.ok(allToDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CompanyResponseDto> updateCompany(
      @PathVariable("id") @Min(1) int id, @Valid @RequestBody CompanyRequestDto companyRequestDto) {

    Company company = mapper.companyRequestDtoToCompany(companyRequestDto);
    Optional<Company> updatedCompany = companyService.update(id, company);

    if (updatedCompany.isPresent()) {
      CompanyResponseDto companyResponseDto = convertToDto(updatedCompany.get());

      return ResponseEntity.ok(companyResponseDto);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCompany(@PathVariable("id") @Min(1) int id) {
    companyService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  private CompanyResponseDto convertToDto(Company company) {
    CompanyResponseDto companyResponseDto = mapper.companyToCompanyResponseDto(company);

    companyResponseDto.setCustomers(
        company.getCustomers().stream()
            .map(mapper::customerEntityToDto)
            .collect(Collectors.toList()));

    return companyResponseDto;
  }
}
