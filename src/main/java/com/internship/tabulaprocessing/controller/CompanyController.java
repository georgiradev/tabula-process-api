package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.CompanyPatchDto;
import com.internship.tabulaprocessing.dto.CompanyRequestDto;
import com.internship.tabulaprocessing.dto.CompanyResponseDto;
import com.internship.tabulaprocessing.entity.Company;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collections;
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
  private final PatchMapper patchMapper;

  @PostMapping
  public ResponseEntity<CompanyResponseDto> createCompany(
      @Valid @RequestBody CompanyRequestDto companyRequestDto) {

    Company company = mapper.companyRequestDtoToCompany(companyRequestDto);
    Optional<Company> savedCompany = companyService.save(company);

    if (savedCompany.isPresent()) {
      CompanyResponseDto companyResponseDto = mapper.convertToDto(savedCompany.get());

      return ResponseEntity.ok(companyResponseDto);
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CompanyResponseDto> getCompany(@PathVariable("id") @Min(1) int id) {
    Company foundCompany = companyService.find(id);
    CompanyResponseDto companyResponseDto = mapper.convertToDto(foundCompany);

    return ResponseEntity.ok(companyResponseDto);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<CompanyResponseDto>> getCompaniesByName(
      @PathVariable(value = "name") String name) {
    List<Company> companies = companyService.findByName(name);

    if (!companies.isEmpty()) {
      List<CompanyResponseDto> allToDto =
          companies.stream().map(mapper::convertToDto).collect(Collectors.toList());

      return ResponseEntity.ok(allToDto);
    } else {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
    }
  }

  @GetMapping
  public ResponseEntity<PagedResult<CompanyResponseDto>> getAllCompanies(
      @Valid QueryParameter queryParameter) {

    Page<Company> pagedResult = companyService.findAll(queryParameter);
    Page<CompanyResponseDto> pagedResultDto = pagedResult.map(mapper::convertToDto);

    PagedResult<CompanyResponseDto> allToDto =
        new PagedResult<>(
            pagedResultDto.toList(),
            queryParameter.getPage(),
            pagedResultDto.getTotalPages(),
            pagedResult.getTotalElements());

    return ResponseEntity.ok(allToDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CompanyResponseDto> updateCompany(
      @PathVariable("id") @Min(1) int id, @Valid @RequestBody CompanyRequestDto companyRequestDto) {

    Company company = mapper.companyRequestDtoToCompany(companyRequestDto);
    Optional<Company> updatedCompany = companyService.update(id, company);

    if (updatedCompany.isPresent()) {
      CompanyResponseDto companyResponseDto = mapper.convertToDto(updatedCompany.get());

      return ResponseEntity.ok(companyResponseDto);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCompany(@PathVariable("id") @Min(1) int id) {
    companyService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping(
      path = "/{id}",
      consumes = {"application/merge-patch+json"})
  public ResponseEntity<CompanyResponseDto> patchCompany(
      @PathVariable int id, @Valid @RequestBody CompanyPatchDto companyRequestDto) {

    Company foundCompany = companyService.find(id);

    Company company = patchMapper.mapObjectsToCompany(companyRequestDto, foundCompany);
    Optional<Company> updatedCompany = companyService.update(id, company);

    if (updatedCompany.isPresent()) {
      CompanyResponseDto companyResponseDto = mapper.convertToDto(updatedCompany.get());

      return ResponseEntity.ok(companyResponseDto);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
