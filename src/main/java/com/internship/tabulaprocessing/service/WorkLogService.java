package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.dto.WorkLogResponse;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.WorkLog;
import org.springframework.data.domain.Pageable;

public interface WorkLogService {

    PagedResult<WorkLog> getByPage(Pageable page);

    WorkLog findById(int id);

    WorkLog create(WorkLog worklog);

    WorkLog update(WorkLog worklog, int id);

    void delete(int id);
}
