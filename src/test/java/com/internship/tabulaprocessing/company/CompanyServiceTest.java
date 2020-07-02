package com.internship.tabulaprocessing.company;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Company;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.provider.CompanyProvider;
import com.internship.tabulaprocessing.repository.CompanyRepository;
import com.internship.tabulaprocessing.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

  @InjectMocks private CompanyService companyService;

  @Mock private CompanyRepository companyRepository;

  @Test
  void testCompanySave() {
    Company company = CompanyProvider.getCompanyInstance();

    when(companyRepository.findByNameAndAddress(any(String.class), any(String.class)))
        .thenReturn(Optional.empty());
    when(companyRepository.save(any(Company.class))).thenReturn(company);

    assertEquals(Optional.of(company), companyService.save(company));
  }

  @Test
  void testCompanyDuplicateSaveMustFail() {
    Company company = CompanyProvider.getCompanyInstance();

    when(companyRepository.findByNameAndAddress(any(String.class), any(String.class)))
        .thenReturn(Optional.of(company));

    assertThrows(EntityAlreadyPresentException.class, () -> companyService.save(company));
  }

  @Test
  void testFindCompany() {
    Company company = CompanyProvider.getCompanyInstance();

    when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));

    assertEquals(company, companyService.find(company.getId()));
  }

  @Test
  void testFindCompanyThatNotExistShouldFail() {
    Company company = CompanyProvider.getCompanyInstance();

    when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> companyService.find(company.getId()));
  }

  @Test
  void deleteCompany() {
    Company company = CompanyProvider.getCompanyInstance();

    when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));
    companyService.delete(company.getId());
    when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> companyService.delete(company.getId()));
  }

  @Test
  void deleteCompanyByNonExistingIdShouldFail() {
    Company company = CompanyProvider.getCompanyInstance();

    when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> companyService.delete(company.getId()));
  }

  @Test
  void testFindAllCompanies() {
    List<Company> companies = CompanyProvider.getCompaniesInstance();
    Page<Company> paging = new PageImpl<>(companies);
    QueryParameter queryParameter = new QueryParameter();

    when(companyRepository.findAll(any(Pageable.class))).thenReturn(paging);

    assertEquals(paging, companyService.findAll(queryParameter));
  }

  @Test
  void testFindAllCompaniesButNoContentFound() {
    List<Company> companies = Collections.emptyList();
    Page<Company> paging = new PageImpl<>(companies);
    QueryParameter queryParameter = new QueryParameter();

    when(companyRepository.findAll(any(Pageable.class))).thenReturn(paging);

    assertEquals(paging, companyService.findAll(queryParameter));
  }

  @Test
  void testUpdateCompany() {
    Company company = CompanyProvider.getCompanyInstance();

    when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));
    when(companyRepository.saveAndFlush(any(Company.class))).thenReturn(company);

    assertEquals(Optional.of(company), companyService.update(1, company));
  }

  @Test
  void testUpdateCompanyByNonExistingIdShouldFail() {
    Company company = CompanyProvider.getCompanyInstance();

    when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());

    assertThrows(
        EntityNotFoundException.class, () -> companyService.update(company.getId(), company));
  }
}
