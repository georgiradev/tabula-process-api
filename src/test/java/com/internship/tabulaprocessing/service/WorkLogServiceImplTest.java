package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Employee;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.TrackingHistory;
import com.internship.tabulaprocessing.entity.WorkLog;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.WorkLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkLogServiceImplTest {

    @Mock
    WorkLogRepository workLogRepository;

    @Mock
    Mapper mapper;

    @InjectMocks
    WorkLogServiceImpl workLogService;

    @Test
    void testGetById() {
        WorkLog workLog = new WorkLog();
        workLog.setId(1);

        doReturn(Optional.of(workLog)).when(workLogRepository).findById(1);
        WorkLog actual = workLogService.findById(1);

        assertEquals(actual, workLog);
    }

    @Test
    void testGetByNonExistingId() {
        WorkLog workLog = new WorkLog();
        workLog.setId(1);

        when(workLogRepository.findById(workLog.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> workLogService.findById(workLog.getId()));
    }

    @Test
    void testGetByPage() {
        WorkLog workLog1 = new WorkLog();
        workLog1.setId(1);

        WorkLog workLog2 = new WorkLog();
        workLog2.setId(2);

        List<WorkLog> expectedWorkLogs = new ArrayList<>();
        expectedWorkLogs.add(workLog1);
        expectedWorkLogs.add(workLog2);

        Pageable pageable = PageRequest.of(1,2);
        Page<WorkLog> expectedPage = new PageImpl<>(expectedWorkLogs, pageable, expectedWorkLogs.size());

        doReturn(expectedPage).when(workLogRepository).findAll(pageable);
        PagedResult<WorkLog> actual = workLogService.getByPage(pageable);

        assertNotNull(actual);
        assertEquals(expectedPage.getContent(), actual.getElements());
        assertEquals(expectedPage.getContent().get(0), actual.getElements().get(0));
        assertEquals(expectedPage.getTotalPages(), actual.getNumOfTotalPages());
    }

    @Test
    void createTest() {
        Employee assignee = new Employee();
        assignee.setId(1);

        Employee employee = new Employee();
        employee.setId(1);

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(1);
        trackingHistory.setAssignee(assignee);

        WorkLog workLog = new WorkLog();
        workLog.setId(1);
        workLog.setTrackingHistory(trackingHistory);
        workLog.setEmployee(employee);

        doReturn(0).when(workLogRepository)
                .numberOfRepeatingWorkLogs(workLog.getEmployee().getId(),
                        workLog.getTrackingHistory().getId(),
                        workLog.getId());

        assertFalse(workLogService.isWorkLogAlreadyCreated(workLog));
        assertEquals(workLog.getEmployee().getId(), workLog.getTrackingHistory().getAssignee().getId());

        when(workLogRepository.save(workLog)).thenReturn(workLog);
        WorkLog actual = workLogService.create(workLog);

        assertNotNull(actual);
        assertEquals(actual, workLog);
    }

    @Test
    void createTestWhenEntityAlreadyExists() {
        Employee employee = new Employee();
        employee.setId(1);

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(1);

        WorkLog workLog = new WorkLog();
        workLog.setId(1);
        workLog.setTrackingHistory(trackingHistory);
        workLog.setEmployee(employee);

        doReturn(1).when(workLogRepository)
                .numberOfRepeatingWorkLogs(workLog.getEmployee().getId(),
                                           workLog.getTrackingHistory().getId(),
                                           workLog.getId());

        assertTrue(workLogService.isWorkLogAlreadyCreated(workLog));
        assertThrows(EntityAlreadyPresentException.class, () -> workLogService
                .create(workLog));
    }

    @Test
    void updatePatchTest() {
        Employee assignee = new Employee();
        assignee.setId(1);

        Employee employee = new Employee();
        employee.setId(1);

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(1);
        trackingHistory.setAssignee(assignee);

        WorkLog workLogUpdate = new WorkLog();
        workLogUpdate.setId(1);
        workLogUpdate.setTrackingHistory(trackingHistory);
        workLogUpdate.setEmployee(employee);

        doReturn(Optional.of(workLogUpdate)).when(workLogRepository).findById(workLogUpdate.getId());
        WorkLog found = workLogService.findById(workLogUpdate.getId());

        assertEquals(found, workLogUpdate);

        when(workLogRepository.save(workLogUpdate)).thenReturn(workLogUpdate);
        WorkLog actual = workLogService.updatePatch(workLogUpdate, workLogUpdate.getId());

        assertEquals(actual, workLogUpdate);
    }

    @Test
    void patchUpdateTestIfAlreadyIsCreated() {
        Employee employee = new Employee();
        employee.setId(1);

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(1);

        WorkLog workLogUpdate = new WorkLog();
        workLogUpdate.setId(1);
        workLogUpdate.setTrackingHistory(trackingHistory);
        workLogUpdate.setEmployee(employee);

        when(workLogRepository.findById(workLogUpdate.getId())).thenReturn(Optional.of(workLogUpdate));

        doReturn(1).when(workLogRepository)
                .numberOfRepeatingWorkLogs(workLogUpdate.getEmployee().getId(),
                 workLogUpdate.getTrackingHistory().getId(),
                 workLogUpdate.getId());

        assertTrue(workLogService.isWorkLogAlreadyCreated(workLogUpdate));
        assertThrows(EntityAlreadyPresentException.class, () -> workLogService
                .updatePatch(workLogUpdate, workLogUpdate.getId()));
    }

    @Test
    void patchUpdateTestWithNonExistingId() {
        WorkLog workLogUpdate = new WorkLog();
        workLogUpdate.setId(1);

        doReturn(Optional.empty()).when(workLogRepository).findById(workLogUpdate.getId());
        assertThrows(EntityNotFoundException.class, () -> workLogService
                .updatePatch(workLogUpdate, workLogUpdate.getId()));
    }

    @Test
    void updateTestWithNonExistingId() {
        WorkLog workLogUpdate = new WorkLog();
        workLogUpdate.setId(1);

        doReturn(Optional.empty()).when(workLogRepository).findById(workLogUpdate.getId());
        assertThrows(EntityNotFoundException.class, () -> workLogService
                .update(workLogUpdate, workLogUpdate.getId()));
    }

    @Test
    void updateTestIfAlreadyIsCreated() {
        Employee employee = new Employee();
        employee.setId(1);

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(1);

        WorkLog workLogUpdate = new WorkLog();
        workLogUpdate.setId(1);
        workLogUpdate.setTrackingHistory(trackingHistory);
        workLogUpdate.setEmployee(employee);

        when(workLogRepository.findById(workLogUpdate.getId())).thenReturn(Optional.of(workLogUpdate));

        doReturn(1).when(workLogRepository)
                .numberOfRepeatingWorkLogs(workLogUpdate.getEmployee().getId(),
                        workLogUpdate.getTrackingHistory().getId(),
                        workLogUpdate.getId());

        assertTrue(workLogService.isWorkLogAlreadyCreated(workLogUpdate));
        assertThrows(EntityAlreadyPresentException.class, () -> workLogService
                .update(workLogUpdate, workLogUpdate.getId()));
    }

    @Test
    void updateTest() {
        Employee assignee = new Employee();
        assignee.setId(1);

        Employee employee = new Employee();
        employee.setId(1);

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(1);
        trackingHistory.setAssignee(assignee);

        WorkLog workLogUpdate = new WorkLog();
        workLogUpdate.setId(1);
        workLogUpdate.setTrackingHistory(trackingHistory);
        workLogUpdate.setEmployee(employee);

        doReturn(Optional.of(workLogUpdate)).when(workLogRepository).findById(workLogUpdate.getId());
        WorkLog found = workLogService.findById(workLogUpdate.getId());

        assertEquals(found, workLogUpdate);

        when(workLogRepository.save(workLogUpdate)).thenReturn(workLogUpdate);
        WorkLog actual = workLogService.update(workLogUpdate, workLogUpdate.getId());

        assertEquals(actual, workLogUpdate);
    }

    @Test
    void deleteTest() {
       WorkLog workLog = new WorkLog();
       workLog.setId(1);

       when(workLogRepository.findById(workLog.getId())).thenReturn(Optional.of(workLog));
       workLogService.delete(workLog.getId());

       verify(workLogRepository).deleteById(workLog.getId());
    }

    @Test
    void deleteTestWithNonExistingId() {
        WorkLog workLog = new WorkLog();
        workLog.setId(1);

        when(workLogRepository.findById(workLog.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> workLogService.delete(workLog.getId()));
    }
}
