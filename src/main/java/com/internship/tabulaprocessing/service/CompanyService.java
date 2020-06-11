package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Company;
import com.internship.tabulaprocessing.exception.CompanyConflictException;
import com.internship.tabulaprocessing.exception.CompanyNotFoundException;
import com.internship.tabulaprocessing.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;

  public Optional<Company> save(Company company) {
    Optional<Company> foundCompany = companyRepository.findByNameAndAddress(company.getName(), company.getAddress());

    if (foundCompany.isPresent()) {
      throw new CompanyConflictException("Company already in database");
    }

    return Optional.of(companyRepository.save(company));
  }

  public Optional<Company> find(int id) {
    return Optional.of(companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found with id " + id)));
  }

  public void delete(int id) {
    find(id);
    companyRepository.deleteById(id);
  }

  public List<Company> findAll(int pageNo) {
    Pageable paging = PageRequest.of(pageNo, 10);
    Page<Company> pagedResult = companyRepository.findAll(paging);

    if (pagedResult.hasContent()) {
      return pagedResult.getContent();
    } else {
      return Collections.emptyList();
    }
  }

  public Optional<Company> update(int id, Company theCompany) {
    Optional<Company> foundCompany = companyRepository.findById(id);

    if (foundCompany.isPresent()) {
      Company company = foundCompany.get();

      company.setName(theCompany.getName());
      company.setAddress(theCompany.getAddress());
      company.setCity(theCompany.getCity());
      company.setCountry(theCompany.getCountry());
      company.setDiscountRate(theCompany.getDiscountRate());
      company.setVatNumber(theCompany.getVatNumber());

      // ToDo *** need customer findAll method to get customers for this company

      return Optional.of(companyRepository.saveAndFlush(company));
    }

    throw new CompanyNotFoundException("Company not found with id " + id);
  }
}
