package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.TimeOff;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TimeOffService {
    TimeOff create (TimeOff timeOff);

    TimeOff update (TimeOff timeOff, int id);

    TimeOff findById(int id);

    PagedResult<TimeOff> findAll(Pageable pageable);

    void delete(int id);
}
