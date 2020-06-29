package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.exception.NotAllowedException;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.TimeOffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TimeOffServiceImplTest {
    @InjectMocks
    private TimeOffServiceImpl service;

    @Mock
    private TimeOffRepository timeOffRepository;

    @Mock
    private Mapper mapper;

    @Test
    void testGetById() {
        TimeOff timeOff = new TimeOff();

        timeOff.setId(1);
        timeOff.setStatus(TimeOffStatus.PENDING);

        doReturn(Optional.of(timeOff)).when(timeOffRepository).findById(1);
        Optional<TimeOff> actual = service.findById(1);

        assertEquals(actual.get(), timeOff);
    }

    @Test
    void testGetByNonExistingId() {
        TimeOff timeOff = new TimeOff();

        timeOff.setId(1);
        timeOff.setStatus(TimeOffStatus.PENDING);

        when(timeOffRepository.findById(timeOff.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(timeOff.getId()));
    }

    @Test
    void testGetByPage() {
        TimeOff timeOff1 = new TimeOff();

        timeOff1.setId(1);
        timeOff1.setStatus(TimeOffStatus.PENDING);

        TimeOff timeOff2 = new TimeOff();

        timeOff2.setId(2);
        timeOff2.setStatus(TimeOffStatus.PENDING);

        List<TimeOff> expectedTimeOffs = new ArrayList<>();
        expectedTimeOffs.add(timeOff1);
        expectedTimeOffs.add(timeOff2);

        Pageable pageable = PageRequest.of(1,2);
        Page<TimeOff> expectedPage = new PageImpl<>(expectedTimeOffs, pageable, expectedTimeOffs.size());

        doReturn(expectedPage).when(timeOffRepository).findAll(pageable);
        PagedResult<TimeOff> actual = service.findAll(pageable);

        assertNotNull(actual);
        assertEquals(expectedPage.getContent(), actual.getElements());
        assertEquals(expectedPage.getContent().get(0), actual.getElements().get(0));
        assertEquals(expectedPage.getTotalPages(), actual.getNumOfTotalPages());
    }

    @Test
    void createTest() {
        TimeOff expected = new TimeOff();

        expected.setId(1);
        expected.setStatus(TimeOffStatus.PENDING);
        expected.setStartDateTime(LocalDateTime.of(2020,7,30, 9,30));
        expected.setEndDateTime(LocalDateTime.of(2020,7,30, 18,30));

        when(timeOffRepository.save(expected)).thenReturn(expected);
        TimeOff actual = service.create(expected);

        assertEquals(actual, expected);
    }

    @Test
    void createTestWithInvalidDate() {
        TimeOff expected = new TimeOff();

        expected.setId(1);
        expected.setStatus(TimeOffStatus.PENDING);
        expected.setEndDateTime(LocalDateTime.of(2020,7,30, 9,30));
        expected.setStartDateTime(LocalDateTime.of(2020,7,30, 18,30));

        assertThrows(NotAllowedException.class, () -> service.create(expected));
    }

    @Test
    void createAlreadyExistingTimeOff() {
        TimeOff timeOff = new TimeOff();

        timeOff.setId(1);
        timeOff.setStatus(TimeOffStatus.PENDING);
        timeOff.setEndDateTime(LocalDateTime.of(2020,7,30, 18,30));
        timeOff.setStartDateTime(LocalDateTime.of(2020,7,30, 9,30));
        timeOff.setApprover(new Employee(1, BigDecimal.valueOf(1), 1, null, null));
        timeOff.setEmployee(new Employee(2, BigDecimal.valueOf(2), 2, null, null));

        TimeOff timeOff2 = new TimeOff();

        timeOff2.setId(1);
        timeOff2.setStatus(TimeOffStatus.PENDING);
        timeOff2.setEndDateTime(LocalDateTime.of(2020,7,30, 18,30));
        timeOff2.setStartDateTime(LocalDateTime.of(2020,7,30, 9,30));
        timeOff2.setApprover(new Employee(1, BigDecimal.valueOf(1), 1, null, null));
        timeOff2.setEmployee(new Employee(2, BigDecimal.valueOf(2), 2, null, null));

        List<TimeOff> timeOffs = new ArrayList<>();
        timeOffs.add(timeOff);
        timeOffs.add(timeOff2);

        when(timeOffRepository.findAll()).thenReturn(timeOffs);
        service.create(timeOff2);

        assertThrows(EntityAlreadyPresentException.class, () -> service.create(timeOff2));
    }

    @Test
    void updateTest() {
        TimeOff expected = new TimeOff();

        expected.setId(1);
        expected.setStatus(TimeOffStatus.PENDING);
        expected.setEndDateTime(LocalDateTime.of(2020,06,30, 9,30));
        expected.setStartDateTime(LocalDateTime.of(2020,06,30, 18,30));

        doReturn(Optional.of(expected)).when(timeOffRepository).findById(expected.getId());
        Optional<TimeOff> actual = service.findById(expected.getId());

        assertEquals(actual.get(), expected);

        when(timeOffRepository.save(expected)).thenReturn(expected);
        TimeOff updatedTimeOff = service.update(expected, expected.getId());

        assertEquals(expected.getId(), updatedTimeOff.getId());
    }

    @Test
    void updateTestIfEntityStatusChanged() {
        TimeOff expected = new TimeOff();

        expected.setId(1);
        expected.setStatus(TimeOffStatus.APPROVED);
        expected.setEndDateTime(LocalDateTime.of(2020,06,30, 9,30));
        expected.setStartDateTime(LocalDateTime.of(2020,06,30, 18,30));

        doReturn(Optional.of(expected)).when(timeOffRepository).findById(expected.getId());
        Optional<TimeOff> actual = service.findById(expected.getId());

        assertEquals(actual.get(), expected);
        assertThrows(NotAllowedException.class, () -> service.update(expected, expected.getId()));
    }

    void updateTestIfEntityIsAlreadyCreated() {
        TimeOff expected = new TimeOff();

        expected.setId(1);
        expected.setStatus(TimeOffStatus.APPROVED);
        expected.setEndDateTime(LocalDateTime.of(2020,06,30, 18,30));
        expected.setStartDateTime(LocalDateTime.of(2020,06,30, 9,30));

        doReturn(Optional.of(expected)).when(timeOffRepository).findById(expected.getId());
        Optional<TimeOff> actual = service.findById(expected.getId());

        assertEquals(actual.get(), expected);
        assertThrows(NotAllowedException.class, () -> service.update(expected, expected.getId()));
    }

    @Test
    void deleteTest() {
        TimeOff timeOff = new TimeOff();

        timeOff.setId(1);
        timeOff.setStatus(TimeOffStatus.PENDING);

        when(timeOffRepository.findById(timeOff.getId())).thenReturn(Optional.of(timeOff));
        service.delete(timeOff.getId());
        when(service.findById(timeOff.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(timeOff.getId()));
    }

    @Test
    void deleteTestIfStatusIsChanged() {
        TimeOff timeOff = new TimeOff();

        timeOff.setId(1);
        timeOff.setStatus(TimeOffStatus.APPROVED);

        when(timeOffRepository.findById(timeOff.getId())).thenReturn(Optional.of(timeOff));
        assertThrows(NotAllowedException.class, () -> service.delete(timeOff.getId()));
    }

    @Test
    void deleteRequestTest() {
        TimeOff timeOff = new TimeOff();

        timeOff.setId(1);
        timeOff.setStatus(TimeOffStatus.PENDING_DELETION);

        when(timeOffRepository.findById(timeOff.getId())).thenReturn(Optional.of(timeOff));
        service.delete(timeOff.getId());
        when(timeOffRepository.findById(timeOff.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deleteRequest(timeOff.getId()));
    }

    @Test
    void deleteRequestTestThrowsError() {
        TimeOff timeOff = new TimeOff();

        timeOff.setId(1);
        timeOff.setStatus(TimeOffStatus.APPROVED);

        when(timeOffRepository.findById(timeOff.getId())).thenReturn(Optional.of(timeOff));
        assertThrows(NotAllowedException.class, () -> service.deleteRequest(timeOff.getId()));
    }

    @Test
    void testIsAlreadyCreated() {
        TimeOff timeOff = new TimeOff();
        timeOff.setTimeOffType(new TimeOffType(1, TypeName.PARENTAL_LEAVE, true));
        timeOff.setEndDateTime(LocalDateTime.of(2020,8,2, 9,30));
        timeOff.setStartDateTime(LocalDateTime.of(2020, 7, 15, 9,30));
        timeOff.setApprover(new Employee(1, BigDecimal.valueOf(1), 1, null, null));
        timeOff.setEmployee(new Employee(2, BigDecimal.valueOf(2), 2, null, null));

        TimeOff timeOff2 = new TimeOff();
        timeOff2.setTimeOffType(new TimeOffType(1, TypeName.PARENTAL_LEAVE, true));
        timeOff2.setEndDateTime(LocalDateTime.of(2020,8,2, 9,30));
        timeOff2.setStartDateTime(LocalDateTime.of(2020, 7, 15, 9,30));
        timeOff2.setApprover(new Employee(1, BigDecimal.valueOf(1), 1, null, null));
        timeOff2.setEmployee(new Employee(2, BigDecimal.valueOf(2), 2, null, null));

        /*doReturn(Mockito.any()).when(timeOffRepository).duplicatesCount(timeOff2.getStartDateTime(),
                timeOff2.getEndDateTime(),timeOff2.getEmployee().getId(),
                timeOff2.getApprover().getId());

        boolean result = service.isAlreadyCreated(timeOff2);
        assertTrue(result);*/
    }
}
