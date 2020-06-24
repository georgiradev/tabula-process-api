package com.internship.tabulaprocessing.service;


import com.internship.tabulaprocessing.dto.MediaExtraDto;
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MediaExtraServiceTest {

    @Mock
    private MediaExtraRepository mediaExtraRepository;

    @InjectMocks
    private MediaExtraService mediaExtraService;

    @Test
    void getOne() {
        when(mediaExtraRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaExtraService.getOne(Mockito.anyInt()));
    }

    @Test
    void update() {
        MediaExtraDto mediaExtraDto = new MediaExtraDto();
        mediaExtraDto.setId(5);
        when(mediaExtraRepository.findById(5)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaExtraService.update(5, mediaExtraDto));
    }
}