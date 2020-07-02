package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.TimeOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeOffRepository extends JpaRepository<TimeOff, Integer> {
    @Query(
            value = "SELECT COUNT(t.id) FROM timeOffs AS t " +
                    "WHERE t.startDateTime <= :endDateTime " +
                    "AND t.endDateTime >= :startDateTime " +
                    "AND t.employee_id = :employeeId " +
                    "AND t.id <> :timeOffId ;",
            nativeQuery = true)
    int numberOfOverlappingTimeOffs (
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("employeeId") int employeeId,
            @Param("timeOffId") int timeOffId);
}
