package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.TimeOff;
import com.internship.tabulaprocessing.entity.TimeOffStatus;
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
        Optional<TimeOff> foundTimeOff = findById(id);

        if(foundTimeOff.isPresent()) {
            timeOff.setStatus(foundTimeOff.get().getStatus());
        }

        if(timeOff.getStatus().equals(TimeOffStatus.PENDING)) {
            timeOff.setId(id);
            timeOff.setStatus(TimeOffStatus.PENDING);
            return timeOffRepository.save(timeOff);
        }

        throw new NotAllowedException(String
                .format("You cannot update time off request with id = %s," +
                        " because it has already been %s", id ,
                        timeOff.getStatus().toString()));
    }

    @Override
    public Optional<TimeOff> findById(int id) {
        Optional<TimeOff> foundTimeOff = timeOffRepository.findById(id);

        if(foundTimeOff.isEmpty()) {
            throw new EntityNotFoundException(String
                    .format("TimeOff with id = %s is not found!", id));
        }

        return foundTimeOff;
    }

    @Override
    public PagedResult<TimeOff> findAll(Pageable pageable) {
        Page<TimeOff> timeOffs = timeOffRepository.findAll(pageable);
        return new PagedResult<>(timeOffs.toList(), pageable.getPageNumber()+1,
                timeOffs.getTotalPages());
    }

    public List<TimeOff> getAllAsList() {
        return  timeOffRepository.findAll();
    }

    @Override
    public void delete(int id) {
        Optional<TimeOff> foundTimeOff = timeOffRepository.findById(id);

        if(foundTimeOff.isEmpty()) {
            throw new EntityNotFoundException(String
                    .format("TimeOff with id = %s is not found!", id));
        }

        if(foundTimeOff.get().getStatus().equals(TimeOffStatus.PENDING)) {
            timeOffRepository.deleteById(id);
        }

        if(foundTimeOff.get().getStatus().equals(TimeOffStatus.APPROVED)) {
            throw new NotAllowedException(String
                    .format("You cannot delete time off request with id = %s," +
                            " because it has already been APPROVED. A deletion " +
                            "request is send to your manager!", id));

            //TODO: A deletion request to be created
        }

        if(foundTimeOff.get().getStatus().equals(TimeOffStatus.REJECTED)) {
            throw new NotAllowedException(String
                    .format("You cannot delete time off request with id = %s," +
                            " because it has already been REJECTED", id));
        }
    }
}
