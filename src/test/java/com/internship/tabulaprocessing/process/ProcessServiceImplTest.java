package com.internship.tabulaprocessing.process;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.provider.ProcessProvider;
import com.internship.tabulaprocessing.repository.ProcessRepository;
import com.internship.tabulaprocessing.service.ProcessServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
class ProcessServiceImplTest {

  @Mock private ProcessRepository processRepository;

  @InjectMocks private ProcessServiceImpl processService;

  @Test
  public void testGetById() {
    Process process = ProcessProvider.getProcessInstance();

    when(processRepository.findById(1)).thenReturn(Optional.of(process));
    Optional<Process> result = Optional.ofNullable(processService.getOneById(1));

    Assertions.assertThat(result).isNotNull();
    result.ifPresent(value -> assertEquals(process, value));
  }

  @Test
  public void testGetProcessByIdShouldFail() {
    Process process = ProcessProvider.getProcessInstance();

    when(processRepository.findById(process.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> processService.getOneById(process.getId()));
  }

  @Test
  void testCreateProcess() {
    Process process = ProcessProvider.getProcessInstance();

    when(processRepository.findByName(process.getName())).thenReturn(Optional.empty());
    when(processRepository.save(any(Process.class))).thenReturn(process);

    assertEquals(process, processService.create(process));
  }

  @Test
  void testCreateProcessShouldFail() {
    Process process = ProcessProvider.getProcessInstance();

    when(processService.getByName(process.getName())).thenReturn(Optional.of(process));

    assertThrows(EntityAlreadyPresentException.class, () -> processService.create(process));
  }

  @Test
  void testDeleteProcess() {
    Process process = ProcessProvider.getProcessInstance();

    when(processRepository.findById(process.getId())).thenReturn(Optional.of(process));
    processService.delete(process.getId());

    verify(processRepository, times(1)).deleteById(process.getId());
  }

  @Test
  void testDeleteProcessShouldFail() {
    when(processRepository.findById(1)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> processService.delete(1));
  }

  @Test
  void testUpdateProcess() {
    Process shipping = ProcessProvider.getProcessInstance();

    when(processRepository.findById(shipping.getId())).thenReturn(Optional.of(shipping));
    when(processRepository.findByName(shipping.getName())).thenReturn(Optional.empty());
    given(processRepository.save(shipping)).willReturn(shipping);
    Optional<Process> updatedProcess = Optional.ofNullable(processService.update(shipping, 1));

    Assertions.assertThat(updatedProcess).isNotNull();
    verify(processRepository).save(any(Process.class));
  }

  @Test
  void testUpdateProcessShouldFailBecauseOfWrongId() {
    Process process = ProcessProvider.getProcessInstance();

    when(processRepository.findById(process.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> processService.update(process, 1));
  }

  @Test
  void testUpdateProcessShouldFailBecauseOfDuplicateName() {
    Process process = ProcessProvider.getProcessInstance();

    when(processRepository.findById(process.getId())).thenReturn(Optional.of(process));
    when(processRepository.findByName(process.getName())).thenReturn(Optional.of(process));

    assertThrows(EntityAlreadyPresentException.class, () -> processService.update(process, 1));
  }

  @Test
  public void testGetProcessByName() {

    Process process = ProcessProvider.getProcessInstance();

    when(processRepository.findByName(process.getName())).thenReturn(Optional.of(process));
    Optional<Process> result = processService.getByName(process.getName());

    assertEquals(process, result.get());
  }

  @Test
  void testFindAllProcesses() {

    List<Process> processes = ProcessProvider.getProcessesInstance();
    Page<Process> paging = new PageImpl<>(processes);
    QueryParameter queryParameter = new QueryParameter();

    when(processRepository.findAll(any(Pageable.class))).thenReturn(paging);

    assertEquals(paging, processService.findAll(queryParameter.getPageable()));
  }
}
