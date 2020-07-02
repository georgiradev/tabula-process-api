package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.ProcessStage;
import com.internship.tabulaprocessing.exception.DeletionNotAllowedException;
import com.internship.tabulaprocessing.provider.ProcessProvider;
import com.internship.tabulaprocessing.provider.ProcessStageEntityProvider;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
import com.internship.tabulaprocessing.repository.OrderRepository;
import com.internship.tabulaprocessing.repository.ProcessRepository;
import com.internship.tabulaprocessing.repository.ProcessStageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessStageServiceTest {

  @Mock private DepartmentRepository departmentRepository;
  @Mock private ProcessRepository processRepository;
  @Mock private ProcessStageRepository repository;
  @Mock private OrderRepository orderRepository;

  @InjectMocks private ProcessStageServiceImpl service;

  @Test
  void findAll() {

    QueryParameter queryParameter = new QueryParameter();
    queryParameter.setSize(2);

    Page<ProcessStage> stages =
        new PageImpl<>(
            ProcessStageEntityProvider.getStageList(),
            queryParameter.getPageable(),
            queryParameter.getSize());

    when(repository.findAll(queryParameter.getPageable())).thenReturn(stages);

    Page<ProcessStage> allProcessStages = service.findAll(queryParameter.getPageable());

    for (ProcessStage stage : allProcessStages) {
      assertEquals(stage.getDepartment(), stage.getDepartmentEntity().getName());
      assertEquals(stage.getProcess(), stage.getProcessEntity().getName());
      assertEquals(stage.getNextStage(), stage.getNextStageEntity().getName());
    }
  }

  @Test
  void persist() {
    ProcessStage prePersist = ProcessStageEntityProvider.getProcessStage("testStage");
    prePersist.setNextStage(null);

    Department department = ProcessStageEntityProvider.getDepartment();
    Process process = ProcessStageEntityProvider.getProces();
    ProcessStage processStage = ProcessStageEntityProvider.getProcessStage("lastStage");
    processStage.setId(4);
    processStage.setNextStage(null);

    when(repository.save(any())).thenReturn(setId(prePersist, 1));
    when(processRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(process));
    when(departmentRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(department));
    when(repository.findByName(Mockito.anyString())).thenReturn(Optional.empty());

    service.persist(prePersist);
    assertNotNull(prePersist.getDepartmentEntity());
    assertNotNull(prePersist.getProcessEntity());
    assertNotNull(prePersist.getDepartmentEntity());

    when(processRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> service.persist(processStage));
  }

  private ProcessStage setId(ProcessStage processStage, int id) {
    processStage.setId(id);
    return processStage;
  }

  @Test
  void update() {

    ProcessStage prePersist = ProcessStageEntityProvider.getPersistedStage("testStage");

    Department department = ProcessStageEntityProvider.getDepartment();
    Process process = ProcessStageEntityProvider.getProces();
    ProcessStage processStage = ProcessStageEntityProvider.getPersistedStage("nextStage");

    processStage.setId(4);

    when(repository.save(any())).thenReturn(setId(prePersist, 1));
    when(repository.findById(any())).thenReturn(Optional.of(prePersist));
    when(processRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(process));
    when(departmentRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(department));
    when(repository.findByName(Mockito.anyString())).thenReturn(Optional.of(processStage));

    assertDoesNotThrow(() -> service.update(prePersist, 1));
  }

  @Test
  void findByName() {
    when(repository.findByName(anyString())).thenReturn(Optional.of(new ProcessStage()));
    assertDoesNotThrow(() -> service.findByName("test"));
    when(repository.findByName(anyString())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> service.findByName("test"));
  }

  @Test
  void findFirstStageOfProcess() {

    Process process = ProcessProvider.getProcessInstance();
    when(processRepository.findById(anyInt())).thenReturn(Optional.of(process));
    ProcessStage processStage = ProcessStageEntityProvider.getProcessStage("test");

    assertThrows(EntityNotFoundException.class, () -> service.findFirstStageOfProcess(1));
  }

  @Test
  void delete() {

    ProcessStage processStage = ProcessStageEntityProvider.getPersistedStage("test");
    when(repository.findById(anyInt())).thenReturn(Optional.of(processStage));
    when(orderRepository.findAllByProcessStage(any()))
        .thenReturn(Arrays.asList(new Order(), new Order()));
    assertThrows(DeletionNotAllowedException.class, () -> service.delete(1));
  }
}
