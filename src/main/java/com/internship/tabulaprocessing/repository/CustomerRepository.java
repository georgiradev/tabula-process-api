package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

  @Query(
      value =
          "SELECT DISTINCT * FROM customer AS c WHERE c.account_id = :account_id AND c.company_id = :company_id",
      nativeQuery = true)
  List<Customer> findIfPresent(
      @Param("account_id") int accountId, @Param("company_id") int companyId);

  Optional<Customer> findByAccountId(int accountId);
}
