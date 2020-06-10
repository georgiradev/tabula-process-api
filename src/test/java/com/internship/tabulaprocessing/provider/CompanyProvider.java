package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.Company;

import java.util.Collections;
import java.util.List;

public class CompanyProvider {

    public static Company getCompanyInstance() {
        Company company = new Company();

        company.setId(1);
        company.setDiscountRate(5.6);
        company.setName("Google");
        company.setCountry("USA");
        company.setAddress("River Street");
        company.setCity("Mountain View");
        company.setVatNumber("US999999999");

        return company;
    }

    public static List<Company> getCompaniesInstance() {
        return Collections.singletonList(getCompanyInstance());
    }
}
