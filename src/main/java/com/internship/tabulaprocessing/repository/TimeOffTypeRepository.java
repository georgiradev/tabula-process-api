package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.TimeOffType;
import com.internship.tabulaprocessing.entity.TypeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeOffTypeRepository extends JpaRepository<TimeOffType, Integer> {

  @Query(
      value =
          "SELECT DISTINCT * FROM time_off_type AS t WHERE t.name = :name AND t.isPaid = :isPaid",
      nativeQuery = true)
  Optional<TimeOffType> findByNameAndPayment(
      @Param("name") String name, @Param("isPaid") Boolean isPaid);
}
