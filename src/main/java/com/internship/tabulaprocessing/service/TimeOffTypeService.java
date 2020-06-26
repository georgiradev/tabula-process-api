package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.TimeOffType;
import com.internship.tabulaprocessing.entity.TypeName;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.repository.TimeOffTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeOffTypeService {
  private final TimeOffTypeRepository repository;

  public TimeOffType getOneById(int id) {
    Optional<TimeOffType> timeOffType = repository.findById(id);

    if (timeOffType.isEmpty()) {
      throw new EntityNotFoundException("Invalid time off type id : " + id);
    }

    return timeOffType.get();
  }

  public TimeOffType create(TimeOffType timeOffType) {

    // two time off types with the same name can exist only if one of them is paid and the other one
    // is not

    String name = timeOffType.getName().toString();
    Boolean paymentSuggested = timeOffType.isPaid();

    Optional<TimeOffType> timeOffTypeOptional =
        repository.findByNameAndPayment(name, paymentSuggested);

    if (timeOffTypeOptional.isPresent()) {
      throw new EntityAlreadyPresentException(
          "A time off type with this name and type of payment option already exists");
    }

    return repository.save(timeOffType);
  }

  public TimeOffType update(TimeOffType timeOffType, int id) {

    TimeOffType type = getOneById(id);

    String name = timeOffType.getName().toString();
    Boolean paymentSuggested = timeOffType.isPaid();

    Optional<TimeOffType> optionalByNameAndPayment =
        repository.findByNameAndPayment(name, paymentSuggested);

    if (optionalByNameAndPayment.isPresent()) {
      throw new EntityAlreadyPresentException(
          "A time off type with this name and type of payment option already exists");
    }

    type.setName(timeOffType.getName());
    type.setPaid(timeOffType.isPaid());
    return repository.saveAndFlush(type);
  }

  public void delete(int id) {
    getOneById(id);
    repository.deleteById(id);
  }

  public Page<TimeOffType> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public TimeOffType getOneByName(TypeName name) {
    Optional<TimeOffType> timeOffType = repository.findByName(name);

    if (timeOffType.isEmpty()) {
      throw new EntityNotFoundException("Invalid time off type name : " + name);
    }

    return timeOffType.get();
  }
}
