package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.TimeOff;
import com.internship.tabulaprocessing.entity.TimeOffStatus;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.exception.NotAllowedException;
import com.internship.tabulaprocessing.repository.TimeOffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeOffServiceImpl implements TimeOffService {
    private final TimeOffRepository timeOffRepository;

    @Override
    public TimeOff create(TimeOff timeOff) {

        if(isAlreadyCreated(timeOff, 0)) {
            throw new EntityAlreadyPresentException("Cannot create the timeOff" +
                    " since it the employee already has one with overlapping period!");
        }

        if(timeOff.getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new NotAllowedException("The startDateTime field is invalid!");
        }

        if(timeOff.getEndDateTime().isBefore(timeOff.getStartDateTime())) {
            throw new NotAllowedException("The endDateTime field is invalid!");
        }

        timeOff.setStatus(TimeOffStatus.PENDING);
        return timeOffRepository.save(timeOff);
    }

    @Override
    public TimeOff update(TimeOff timeOff, int id) {
        TimeOff foundTimeOff = findById(id);

        if(isAlreadyCreated(timeOff, id)) {
            throw new EntityAlreadyPresentException("Cannot update the timeOff" +
                    " since it the employee already has one with overlapping period!");
        }

        timeOff.setStatus(foundTimeOff.getStatus());

        if(timeOff.getStatus().equals(TimeOffStatus.PENDING)) {

            if(timeOff.getStartDateTime()==null || timeOff.getStartDateTime().isBefore(LocalDateTime.now())) {
                throw new NotAllowedException("The startDateTime field is invalid!");
            }

            if(timeOff.getEndDateTime()==null || timeOff.getEndDateTime().isBefore(timeOff.getStartDateTime())) {
                throw new NotAllowedException("The endDateTime field is invalid!");
            }

            timeOff.setId(id);
            //timeOff.setStatus(TimeOffStatus.PENDING);
            return timeOffRepository.save(timeOff);
        }

        throw new NotAllowedException(String
                .format("You cannot update time off request with id = %s," +
                        " because it has already been %s", id ,
                        timeOff.getStatus().toString()));
    }

    public TimeOff statusUpdate(TimeOff timeOff, int id) {
        findById(id);
        return timeOffRepository.save(timeOff);
    }

    @Override
    public TimeOff findById(int id) {
        Optional<TimeOff> foundTimeOff = timeOffRepository.findById(id);

        if(foundTimeOff.isEmpty()) {
            throw new EntityNotFoundException(String
                    .format("TimeOff with id = %s is not found!", id));
        }
        return foundTimeOff.get();
    }

    @Override
    public PagedResult<TimeOff> findAll(Pageable pageable) {
        Page<TimeOff> timeOffs = timeOffRepository.findAll(pageable);

        return new PagedResult<>(
                   timeOffs.toList(),
                   pageable.getPageNumber()+1,
                   timeOffs.getTotalPages(),
                   timeOffs.getTotalElements());
    }

    public List<TimeOff> getAllAsList() {
        return timeOffRepository.findAll();
    }

    @Override
    public void delete(int id) {
        TimeOff foundTimeOff = findById(id);

        if(foundTimeOff.getStatus().equals(TimeOffStatus.PENDING)) {
            timeOffRepository.deleteById(id);
            return;
        }

        if(foundTimeOff.getStatus().equals(TimeOffStatus.APPROVED)) {

            foundTimeOff.setStatus(TimeOffStatus.PENDING_DELETION);
            foundTimeOff.setId(id);
            timeOffRepository.save(foundTimeOff);

            throw new NotAllowedException(String
                    .format("You cannot delete time off request with id = %s," +
                            " because it has already been APPROVED. A deletion" +
                            " request is send!", id));
        }

        if(foundTimeOff.getStatus().equals(TimeOffStatus.REJECTED)) {
            throw new NotAllowedException(String
                    .format("You cannot delete time off request with id = %s," +
                            " because it has already been REJECTED", id));
        }
    }

    public void deleteByManager(int id) {
        TimeOff foundTimeOff = findById(id);

        if(foundTimeOff.getStatus().equals(TimeOffStatus.PENDING_DELETION)) {
            timeOffRepository.deleteById(id);
            return;
        }

        throw new NotAllowedException(String.format("You cannot delete time off request with id = %s", id));
    }

    public boolean isAlreadyCreated (TimeOff timeOff, int timeOffId) {
        return  (timeOffRepository.numberOfOverlappingTimeOffs(
                timeOff.getStartDateTime(),
                timeOff.getEndDateTime(),
                timeOff.getEmployee().getId(),
                timeOffId)>=1);
    }
}
