package com.internship.tabulaprocessing.media;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.dto.EmployeeResponseDto;
import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.provider.MediaExtraProvider;
import com.internship.tabulaprocessing.provider.MediaProvider;
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import com.internship.tabulaprocessing.repository.MediaRepository;
import com.internship.tabulaprocessing.service.MediaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

    @Mock
    private MediaRepository mediaRepository;

    @InjectMocks
    private MediaService mediaService;

    @Mock
    private Mapper mapper;

    @Test
    void getOne() {
        when(mediaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaService.getOne(Mockito.anyInt()));
    }

    @Test
    void getAllIfEmpty() {
        QueryParameter queryParameter = new QueryParameter();
        Mockito.when(mediaRepository.findAll(queryParameter.getPageable()))
                .thenReturn(new PageImpl<>(new ArrayList<>(), queryParameter.getPageable(), 5));

        PagedResult<MediaDto> employeeDtoPagedResult= mediaService.getAll(queryParameter.getPageable());
        assertTrue(employeeDtoPagedResult.getElements().isEmpty());
    }

    @Test
    void create (){
        MediaExtra mediaExtra = MediaExtraProvider.getMediaExtraInstance();

        Media media = MediaProvider.getMediaInstance();
        media.setMediaExtras(Collections.singleton(mediaExtra));

        MediaDto mediaDto = new MediaDto();

        mediaDto.setMediaExtraIds(
                media.getMediaExtras().stream()
                        .map(mediaExtra1 -> String.valueOf(mediaExtra1.getId()))
                        .collect(Collectors.toList())
        );

        mediaDto.setId(media.getId());
        mediaDto.setName(media.getName());
        mediaDto.setPrice(media.getPrice());

        when(mapper.convertToMediaDTO(any())).thenReturn(mediaDto);
        when(mediaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(media));

        MediaDto actualMedia = mediaService.getOne(1);

        assertEquals(media.getId(), actualMedia.getId());
        assertEquals(media.getName(), actualMedia.getName());

    }

    @Test
    void DeleteById(){
        mediaService.deleteById(89);
        verify(mediaRepository, times(1)).deleteById(eq(89));
    }

    @Test
    void update() {
        MediaDto mediaDto = new MediaDto();
        mediaDto.setId(5);
        when(mediaRepository.findById(5)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaService.update(5, mediaDto));
    }

}

