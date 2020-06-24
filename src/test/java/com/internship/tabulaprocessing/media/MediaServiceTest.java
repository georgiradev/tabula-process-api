package com.internship.tabulaprocessing.media;

import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
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
    @Mock
    private MediaExtraRepository mediaExtraRepository;

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
    void getAll() {
        Media media = new Media();
        media.setId(1);
        media.setName("name1");
        media.setPrice(BigDecimal.valueOf(1));

        Media media2 = new Media();
        media2.setId(2);
        media2.setName("name2");
        media2.setPrice(BigDecimal.valueOf(2));

        List<Media> expectedMedia = new ArrayList<>();
        expectedMedia.add(media);
        expectedMedia.add(media2);

        Pageable pageable = PageRequest.of(1,2);
        Page<Media> expectedPage = new PageImpl<>(expectedMedia, pageable, expectedMedia.size());

        doReturn(expectedPage).when(mediaRepository).findAll(pageable);
        PagedResult<MediaDto> actual = mediaService.getAll(pageable);

        assertNotNull(actual);
        assertEquals(mapper.convertToMediaDtoList(expectedPage.getContent()), actual.getElements());
        assertEquals(expectedPage.getContent().get(0), expectedMedia.get(0));
        assertEquals(expectedPage.getTotalPages(), actual.getNumOfTotalPages());
    }

    @Test
    void create (){
        MediaExtra mediaExtra = new MediaExtra();
        mediaExtra.setId(1);
        mediaExtra.setName("name1");
        mediaExtra.setPrice(BigDecimal.valueOf(15));

        Media media = new Media();
        media.setId(1);
        media.setName("name2");
        media.setPrice(BigDecimal.valueOf(10));
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

