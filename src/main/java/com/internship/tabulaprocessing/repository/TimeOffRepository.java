package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.TimeOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface TimeOffRepository extends JpaRepository<TimeOff, Integer> {
    @Query(
            value = "SELECT Count(DISTINCT(t.id)) FROM timeOffs AS t " +
                    "WHERE t.startDateTime = :startDateTime " +
                    "AND t.endDateTime = :endDateTime " +
                    "AND t.employee_id = :employeeId ; ",
            nativeQuery = true)
    int duplicatesCount(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("employeeId") int employeeId);
}
