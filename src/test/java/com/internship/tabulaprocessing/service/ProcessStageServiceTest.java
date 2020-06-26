package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.ProcessStage;
import com.internship.tabulaprocessing.provider.ProcessStageEntityProvider;
import com.internship.tabulaprocessing.repository.DepartmentRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProcessStageServiceTest {

  @Mock private DepartmentRepository departmentRepository;
  @Mock private ProcessRepository processRepository;
  @Mock private ProcessStageRepository repository;

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

    Mockito.when(repository.findAll(queryParameter.getPageable())).thenReturn(stages);

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

    Mockito.when(repository.save(Mockito.any())).thenReturn(setId(prePersist, 1));
    Mockito.when(processRepository.findByName(Mockito.anyString()))
        .thenReturn(Optional.of(process));
    Mockito.when(departmentRepository.findByName(Mockito.anyString()))
        .thenReturn(Optional.of(department));
    Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(Optional.empty());

    service.persist(prePersist);
    assertNotNull(prePersist.getDepartmentEntity());
    assertNotNull(prePersist.getProcessEntity());
    assertNotNull(prePersist.getDepartmentEntity());

    Mockito.when(processRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
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

    Mockito.when(repository.save(Mockito.any())).thenReturn(setId(prePersist, 1));
    Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(prePersist));
    Mockito.when(processRepository.findByName(Mockito.anyString()))
        .thenReturn(Optional.of(process));
    Mockito.when(departmentRepository.findByName(Mockito.anyString()))
        .thenReturn(Optional.of(department));
    Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(Optional.of(processStage));

    assertDoesNotThrow(() -> service.update(prePersist, 1));
  }

  @Test
  void delete() {

    ProcessStage stage = ProcessStageEntityProvider.getPersistedStage("testStage");
    stage.setName("test");
    stage.setDepartment("test");
    stage.setNextStage("test");

    Mockito.when(repository.findById(Mockito.anyInt()))
        .thenReturn(Optional.of(ProcessStageEntityProvider.getPersistedStage("test")));
    Mockito.when(repository.findByNextStageEntityId(Mockito.anyInt()))
        .thenReturn(ProcessStageEntityProvider.getPersistedStage("test"));
    repository.deleteById(stage.getId());
    assertDoesNotThrow(() -> service.delete(1));
  }
}
