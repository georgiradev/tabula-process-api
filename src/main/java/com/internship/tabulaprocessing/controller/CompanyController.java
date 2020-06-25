package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.CompanyRequestDto;
import com.internship.tabulaprocessing.dto.CompanyResponseDto;
import com.internship.tabulaprocessing.entity.Company;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
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
    CompanyResponseDto companyResponseDto = mapper.companyToCompanyResponseDto(savedCompany.get());

    return ResponseEntity.ok(companyResponseDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CompanyResponseDto> getCompany(@PathVariable("id") @Min(1) int id) {
    Optional<Company> foundCompany = companyService.find(id);

    if (foundCompany.isPresent()) {
      CompanyResponseDto companyResponseDto =
          mapper.companyToCompanyResponseDto(foundCompany.get());

      return ResponseEntity.ok(companyResponseDto);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @GetMapping
  public ResponseEntity<List<CompanyResponseDto>> getAllCompanies(
      @RequestParam(defaultValue = "0") int pageNo) {

    List<Company> allCompanies = companyService.findAll(pageNo);
    List<CompanyResponseDto> allToDto =
        allCompanies.stream().map(mapper::companyToCompanyResponseDto).collect(Collectors.toList());

    return ResponseEntity.ok(allToDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CompanyResponseDto> updateCompany(
      @PathVariable("id") @Min(1) int id, @Valid @RequestBody CompanyRequestDto companyRequestDto) {

    Company company = mapper.companyRequestDtoToCompany(companyRequestDto);
    Optional<Company> updatedCompany = companyService.update(id, company);

    if (updatedCompany.isPresent()) {
      CompanyResponseDto companyResponseDto =
          mapper.companyToCompanyResponseDto(updatedCompany.get());

      return ResponseEntity.ok(companyResponseDto);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCompany(@PathVariable("id") @Min(1) int id) {
    companyService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
