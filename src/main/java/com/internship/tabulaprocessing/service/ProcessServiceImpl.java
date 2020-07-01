package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProcessServiceImpl {

  @Autowired private ProcessRepository processRepository;

  public Process getOneById(int id) {
    Optional<Process> processOptional = processRepository.findById(id);

    if (!processOptional.isPresent()) {
      throw new EntityNotFoundException("Invalid process id : " + id);
    }

    return processOptional.get();
  }

  public Process create(Process process) {
    Optional<Process> processOptional = getByName(process.getName());

    if (!processOptional.isPresent()) {
      return processRepository.save(process);
    }
    throw new EntityAlreadyPresentException("A process with this name already exists");
  }

  public Process update(Process process, int id) {

    getOneById(id);

    String name = process.getName();
    Optional<Process> processOptionalByName = getByName(name);

    if (processOptionalByName.isPresent())
        throw new EntityAlreadyPresentException("A process with this name already exists");

    process.setId(id);

    return processRepository.save(process);
  }

  public void delete(int id) {
    getOneById(id);
    processRepository.deleteById(id);
  }

  public Page<Process> findAll(Pageable pageable) {
    return processRepository.findAll(pageable);
  }

  public Optional<Process> getByName(String name) {

    return processRepository.findByName(name);
  }
}
