package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcessServiceImpl {

    private final ProcessRepository processRepository;

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

        Optional<Process> processOptional = processRepository.findById(id);

        if (!processOptional.isPresent()) {
            throw new EntityNotFoundException("A process with this id does not exist");
        }
        String name = process.getName();
        Optional<Process> processOptionalByName = processRepository.findByName(name);

        if (processOptionalByName.isPresent())
            if (processOptionalByName.get().getName().equals(process.getName())) {
                throw new EntityAlreadyPresentException("A process with this name already exists");
            }
        process.setId(id);

        return processRepository.save(process);
    }

    public void delete(int id) {
        Optional<Process> processOptional = processRepository.findById(id);
        if (!processOptional.isPresent()) {
            throw new EntityNotFoundException("A process with this id does not exist");
        }

        processRepository.deleteById(id);
    }

    public List<Process> findAll(int pageNo) {
        Pageable paging = PageRequest.of(pageNo, 10);
        Page<Process> pagedResult = processRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            throw new EntityNotFoundException("No processes");
        }
    }

    public Optional<Process> getByName(String name) {

        return processRepository.findByName(name);
    }
}
