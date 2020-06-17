package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> findById(int id);

    Optional<Company> findByName(String name);

    @Query(value = "SELECT DISTINCT * FROM company AS c WHERE c.name = :name AND c.address = :address", nativeQuery = true)
    Optional<Company> findByNameAndAddress(
            @Param("name") String name, @Param("address") String address);
}
