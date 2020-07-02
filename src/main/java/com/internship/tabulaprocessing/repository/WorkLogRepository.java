package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Integer> {
    @Query(
            value = "SELECT Count(w.id) FROM worklogs AS w " +
                    "WHERE w.employee_id = :employeeId " +
                    "AND w.trackingHistory_id = :trackingHistoryId " +
                    "AND w.id <> :workLogId ;",
            nativeQuery = true)
    int numberOfRepeatingWorkLogs (@Param("employeeId") int employeeId,
                                   @Param("trackingHistoryId") int trackingHistoryId,
                                   @Param("workLogId") int workLogId);
}
