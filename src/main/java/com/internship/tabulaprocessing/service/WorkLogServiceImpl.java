package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.WorkLog;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.exception.NotAllowedException;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkLogServiceImpl implements WorkLogService {
    private final WorkLogRepository workLogRepository;

    @Override
    public PagedResult<WorkLog> getByPage(Pageable page) {
        Page<WorkLog> allWorkLogs = workLogRepository.findAll(page);
        return new PagedResult<>(
                allWorkLogs.toList(),
                allWorkLogs.getTotalPages(),
                page.getPageNumber()+1,
                allWorkLogs.getTotalElements());
    }

    @Override
    public WorkLog findById(int id) {
        Optional<WorkLog> workLog = workLogRepository.findById(id);

        if(workLog.isEmpty()) {
            throw new EntityNotFoundException(String.format("WorkLog with id %s is not found.", id));
        }
        return workLog.get();
    }

    @Override
    public WorkLog create(WorkLog worklog) {
        if(isWorkLogAlreadyCreated(worklog)) {
           throw new EntityAlreadyPresentException("WorkLog already created!");
        }

        if(worklog.getTrackingHistory().getAssignee().getId()!=worklog.getEmployee().getId()) {
            throw new NotAllowedException("You are not assigned to work on this order's process stage!");
        }

        return workLogRepository.save(worklog);
    }

    public WorkLog updatePatch (WorkLog worklog, int id) {
        WorkLog foundWorkLog = findById(id);
        worklog.setId(id);
        worklog.setCreatedDateTime(foundWorkLog.getCreatedDateTime());

        if(isWorkLogAlreadyCreated(worklog)) {
            throw new EntityAlreadyPresentException("WorkLog already exists!");
        }

        if(worklog.getTrackingHistory().getAssignee().getId()!=worklog.getEmployee().getId()) {
            throw new NotAllowedException("You are not assigned to work on this order's process stage!");
        }
        return workLogRepository.save(worklog);
    }

    @Override
    public WorkLog update(WorkLog worklog, int id) {
        WorkLog foundWorkLog = findById(id);
        worklog.setId(id);
        worklog.setCreatedDateTime(foundWorkLog.getCreatedDateTime());

        double totalHoursSpent = worklog.getHoursSpent() + foundWorkLog.getHoursSpent();
        worklog.setHoursSpent(totalHoursSpent);

        if(isWorkLogAlreadyCreated(worklog)) {
            throw new EntityAlreadyPresentException("WorkLog already exists!");
        }

        if(worklog.getTrackingHistory().getAssignee().getId()!=worklog.getEmployee().getId()) {
            throw new NotAllowedException("You are not assigned to work on this order's process stage!");
        }
        return workLogRepository.save(worklog);
    }

    @Override
    public void delete(int id) {
        Optional<WorkLog> workLog = workLogRepository.findById(id);

        if(workLog.isEmpty()) {
            throw new EntityNotFoundException(String.format("WorkLog with id %s is not found.",id));
        }
        workLogRepository.deleteById(id);
    }

    public boolean isWorkLogAlreadyCreated(WorkLog workLog) {
        return workLogRepository.numberOfRepeatingWorkLogs(
                workLog.getEmployee().getId(),
                workLog.getTrackingHistory().getId(),
                workLog.getId())>=1;
    }
}
