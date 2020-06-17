package com.internship.tabulaprocessing.mediaextra;

import com.internship.tabulaprocessing.dto.MediaExtraDto;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import com.internship.tabulaprocessing.service.MediaExtraService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MediaExtraServiceTest {

    private Mapper mapper;

    @Mock
    private MediaExtraRepository mediaExtraRepository;

    @InjectMocks
    private MediaExtraService mediaExtraService;

    @Test
    void getAll() {
        List<MediaExtra> mediaExtra = new ArrayList<>();
        Page<MediaExtra> page = new PageImpl<>(mediaExtra);

        when(mediaExtraRepository.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(mediaExtra, mediaExtraService.getAll(anyInt()).getBody());
    }

    @Test
    void getOne() {
        when(mediaExtraRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaExtraService.getOne(Mockito.anyInt()));
    }

    @Test
    void deleteById() {
        MediaExtra mediaExtra = new MediaExtra();
        mediaExtra.setId(1);
        when(mediaExtraRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> mediaExtraService.deleteById(mediaExtra.getId()));
    }

    @Test
    void update() {
        MediaExtraDto mediaExtraDto = new MediaExtraDto();
        mediaExtraDto.setId(5);
        when(mediaExtraRepository.findById(5)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaExtraService.update(5, mediaExtraDto));
    }
}