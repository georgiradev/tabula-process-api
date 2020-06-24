package com.internship.tabulaprocessing.service;


import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.TimeOff;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.TimeOffRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class TimeOffServiceImpl implements TimeOffService {
    private TimeOffRepository timeOffRepository;
    private Mapper mapper;

    @Override
    public TimeOff create(TimeOff timeOff) {
        return null;
    }

    @Override
    public TimeOff update(TimeOff timeOff, int id) {
        return null;
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
        return new PagedResult<>(timeOffs.toList(), pageable.getPageNumber()+1,
                timeOffs.getTotalPages());
    }

    @Override
    public void delete(int id) {
        Optional<TimeOff> foundTimeOff = timeOffRepository.findById(id);
        if(foundTimeOff.isEmpty()) {
            throw new EntityNotFoundException(String
                    .format("TimeOff with id = %s is not found!", id));
        }
        timeOffRepository.deleteById(id);
    }
}
