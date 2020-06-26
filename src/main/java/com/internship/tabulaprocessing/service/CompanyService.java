package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Company;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;

  public Optional<Company> save(Company company) {
    Optional<Company> foundCompany =
            companyRepository.findByNameAndAddress(company.getName(), company.getAddress());

    if (foundCompany.isPresent()) {
      throw new EntityAlreadyPresentException("Company already in database");
    }

    return Optional.of(companyRepository.save(company));
  }

  public Optional<Company> find(int id) {
    return Optional.of(
            companyRepository
                    .findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + id)));
  }

  public void delete(int id) {
    find(id);
    companyRepository.deleteById(id);
  }

  public Page<Company> findAll(QueryParameter queryParameter) {
    return companyRepository.findAll(queryParameter.getPageable());
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

      return Optional.of(companyRepository.saveAndFlush(company));
    }

    throw new EntityNotFoundException("Company not found with id " + id);
  }
}