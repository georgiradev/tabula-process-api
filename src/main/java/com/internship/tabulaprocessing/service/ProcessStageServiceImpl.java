package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Department;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.ProcessStage;
import com.internship.tabulaprocessing.exception.DeletionNotAllowedException;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcessStageServiceImpl implements ProcessStageService {

  private final ProcessStageRepository repository;
  private final DepartmentRepository departmentRepository;
  private final ProcessRepository processRepository;
  private final TrackingHistoryRepository trackingHistoryRepository;
  private final OrderRepository orderRepository;

  @Override
  public ProcessStage persist(ProcessStage processStage) {

    Optional<ProcessStage> optionalProcessStage = repository.findByName(processStage.getName());
    if (optionalProcessStage.isPresent()) {
      throw new EntityAlreadyPresentException(
          String.format("Process Stage with name %s, already present.", processStage.getName()));
    }

    if (processStage.getNextStage() != null) {
      processStage.setNextStageEntity(this.getNextStageByName(processStage.getNextStage()));
    }
    processStage.setDepartmentEntity(this.getDepartmentByName(processStage.getDepartment()));
    processStage.setProcessEntity(this.getProcessByName(processStage.getProcess()));

    return repository.save(processStage);
  }

  @Override
  public ProcessStage update(ProcessStage processStage, int id) {

    ProcessStage oldStage = findById(id);
    processStage.setId(oldStage.getId());

    if (processStage.getNextStage() != null) {
      processStage.setNextStageEntity(this.getNextStageByName(processStage.getNextStage()));
    }
    processStage.setProcessEntity(this.getProcessByName(processStage.getProcess()));
    processStage.setDepartmentEntity(this.getDepartmentByName(processStage.getDepartment()));

    return repository.save(processStage);
  }

  @Override
  public ProcessStage findById(int id) {

    Optional<ProcessStage> optionalProcessStage = repository.findById(id);
    if (!optionalProcessStage.isPresent()) {
      throw new EntityNotFoundException(String.format("ProcessStage with id %s, not found.", id));
    }
    return populateTransientFields(optionalProcessStage.get());
  }

  @Override
  public Page<ProcessStage> findAll(Pageable pageable) {

    Page<ProcessStage> allProcessStages = repository.findAll(pageable);
    for (ProcessStage stage : allProcessStages) {
      populateTransientFields(stage);
    }
    return allProcessStages;
  }

  @Override
  public ProcessStage findByName(String name) {
    Optional<ProcessStage> optional = repository.findByName(name);
    if (!optional.isPresent()) {
      throw new EntityNotFoundException(
          String.format("ProcessStage with name %s, not found", name));
    }
    return optional.get();
  }

  @Override
  public void delete(int id) {

    ProcessStage processStageToBeDeleted = findById(id);
    List<Order> orders = orderRepository.findAllByProcessStage(processStageToBeDeleted);
    if (!orders.isEmpty()) {
      throw new DeletionNotAllowedException(
              String.format(
                      "ProcessStage with id %s, cannot be deleted because it has Order entities associated with it.",id));
    }
    repository.deleteById(id);
  }

  @Override
  public ProcessStage findFirstStageOfProcess(int processId) {

    Optional<Process> process = processRepository.findById(processId);
    if (!process.isPresent()) {
      throw new EntityNotFoundException(String.format("Process with id %s, not found.", processId));
    }

    Optional<ProcessStage> firstProcessStage =
        repository.findByProcessEntityAndFirstStage(process.get(), true);
    if (firstProcessStage.isEmpty()) {
      throw new EntityNotFoundException(
          String.format(
              "Process with id %s, doesn't have a processStage which is marked as first, "
                  + "to set processStage as first, set firstStage=true when creating or updating process stage",
              process.get().getId()));
    }

    return firstProcessStage.get();
  }

  private Department getDepartmentByName(String name) {
    Optional<Department> optionalDepartment = departmentRepository.findByName(name);
    if (!optionalDepartment.isPresent()) {
      throw new EntityNotFoundException(String.format("Department with name %s, not found", name));
    }
    return optionalDepartment.get();
  }

  private Process getProcessByName(String name) {
    Optional<Process> optionalProcess = processRepository.findByName(name);
    if (!optionalProcess.isPresent()) {
      throw new EntityNotFoundException(String.format("Process with name %s, not found", name));
    }
    return optionalProcess.get();
  }

  private ProcessStage getNextStageByName(String name) {
    Optional<ProcessStage> optionalProcessStage = repository.findByName(name);
    if (!optionalProcessStage.isPresent()) {
      throw new EntityNotFoundException(
          String.format("Next Process Stage with name %s, not found", name));
    }
    return optionalProcessStage.get();
  }

  private ProcessStage populateTransientFields(ProcessStage processStage) {

    processStage.setDepartment(processStage.getDepartmentEntity().getName());
    processStage.setProcess(processStage.getProcessEntity().getName());
    if (processStage.getNextStageEntity() != null)
      processStage.setNextStage(processStage.getNextStageEntity().getName());

    return processStage;
  }
}
