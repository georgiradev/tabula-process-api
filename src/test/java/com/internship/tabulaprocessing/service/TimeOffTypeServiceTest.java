package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.TimeOffType;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.provider.TimeOffTypeProvider;
import com.internship.tabulaprocessing.repository.TimeOffTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TimeOffTypeServiceTest {
  @Mock private TimeOffTypeRepository repository;

  @InjectMocks private TimeOffTypeService service;

  @Test
  public void testGetById() {
    TimeOffType timeOffType = TimeOffTypeProvider.getTimeOffTypeInstance();

    when(repository.findById(1)).thenReturn(Optional.of(timeOffType));

    assertEquals(timeOffType, service.getOneById(timeOffType.getId()));
  }

  @Test
  public void testGetTimeOffTypeByIdShouldFail() {
    TimeOffType timeOffType = TimeOffTypeProvider.getTimeOffTypeInstance();

    when(repository.findById(timeOffType.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> service.getOneById(timeOffType.getId()));
  }

  @Test
  void testCreateTimeOffType() {
    TimeOffType timeOffType = TimeOffTypeProvider.getTimeOffTypeInstance();

    String name = timeOffType.getName().toString();
    Boolean payment = timeOffType.getIsPaid();

    when(repository.findByNameAndPayment(name, payment)).thenReturn(Optional.empty());
    when(repository.save(any(TimeOffType.class))).thenReturn(timeOffType);

    assertEquals(timeOffType, service.create(timeOffType));
  }

  @Test
  void testCreateTimeOffTypeShouldFail() {
    TimeOffType timeOffType = TimeOffTypeProvider.getTimeOffTypeInstance();

    String name = timeOffType.getName().toString();
    Boolean payment = timeOffType.getIsPaid();

    when(repository.findByNameAndPayment(name, payment)).thenReturn(Optional.of(timeOffType));

    assertThrows(EntityAlreadyPresentException.class, () -> service.create(timeOffType));
  }

  @Test
  void testDeleteTimeOffType() {
    TimeOffType timeOffType = TimeOffTypeProvider.getTimeOffTypeInstance();

    when(repository.findById(timeOffType.getId())).thenReturn(Optional.of(timeOffType));
    service.delete(timeOffType.getId());

    verify(repository, times(1)).deleteById(timeOffType.getId());
  }

  @Test
  void testDeleteTimeOffTypeShouldFail() {
    when(repository.findById(1)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> service.delete(1));
  }

  @Test
  void testUpdateTimeOffType() {
    TimeOffType timeOffType = TimeOffTypeProvider.getTimeOffTypeInstance();

    String name = timeOffType.getName().toString();
    Boolean payment = timeOffType.getIsPaid();

    when(repository.findById(timeOffType.getId())).thenReturn(Optional.of(timeOffType));
    when(repository.findByNameAndPayment(name, payment)).thenReturn(Optional.empty());
    given(repository.saveAndFlush(timeOffType)).willReturn(timeOffType);

    TimeOffType updatedTimeOffType = service.update(timeOffType, 1);

    assertEquals(timeOffType, service.update(timeOffType, 1));
  }

  @Test
  void testUpdateTimeOffTypeShouldFailBecauseOfWrongId() {
    TimeOffType timeOffType = TimeOffTypeProvider.getTimeOffTypeInstance();

    Mockito.when(repository.findById(1)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> service.update(timeOffType, 1));
  }

  @Test
  void testFindAll() {

    QueryParameter queryParameter = new QueryParameter();
    List<TimeOffType> timeOffTypes = TimeOffTypeProvider.getTimeOffTypesInstance();
    Page<TimeOffType> paging = new PageImpl<>(timeOffTypes);

    when(repository.findAll(any(Pageable.class))).thenReturn(paging);

    assertEquals(paging, service.findAll(queryParameter.getPageable()));
  }
}
