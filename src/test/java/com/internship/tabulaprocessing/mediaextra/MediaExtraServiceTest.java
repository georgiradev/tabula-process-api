package com.internship.tabulaprocessing.mediaextra;

import com.internship.tabulaprocessing.dto.MediaExtraDto;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.provider.MediaExtraProvider;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaExtraServiceTest {

  @Mock private MediaExtraRepository mediaExtraRepository;

  @InjectMocks private MediaExtraService mediaExtraService;

  @Mock private Mapper mapper;

  @Test
  void getOneIfNotExists() {
    when(mediaExtraRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> mediaExtraService.getOne(Mockito.anyInt()));
  }

  @Test
  void getOne() {
    MediaExtra mediaExtra = MediaExtraProvider.getMediaExtraInstance();

    when(mediaExtraRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mediaExtra));
    MediaExtraDto actualMediaExtra = mediaExtraService.getOne(1);

    assertEquals(mapper.convertToMediaExtraDTO(mediaExtra), actualMediaExtra);
  }

  @Test
  void DeleteById() {
    mediaExtraService.deleteById(89);
    verify(mediaExtraRepository, times(1)).deleteById(eq(89));
  }

  @Test
  void update() {
    MediaExtraDto mediaExtraDto =
        mapper.convertToMediaExtraDTO(MediaExtraProvider.getMediaExtraInstance());
    when(mediaExtraRepository.findById(5)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> mediaExtraService.update(5, mediaExtraDto));
  }

  @Test
  void create() {
    MediaExtra mediaExtra = new MediaExtra();
    mediaExtra.setId(1);
    mediaExtra.setName("name1");
    mediaExtra.setPrice(BigDecimal.valueOf(15));

    MediaExtraDto mediaExtraDto = new MediaExtraDto();

    when(mapper.convertToMediaExtraDTO(any())).thenReturn(mediaExtraDto);

    mediaExtraDto.setId(mediaExtra.getId());
    mediaExtraDto.setName(mediaExtra.getName());
    mediaExtra.setPrice(mediaExtra.getPrice());

    when(mediaExtraRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mediaExtra));
    MediaExtraDto actualMediaExtra = mediaExtraService.getOne(1);

    assertEquals(mediaExtra.getId(), actualMediaExtra.getId());
    assertEquals(mediaExtra.getName(), actualMediaExtra.getName());
  }

  @Test
  void getAll() {
    MediaExtra mediaExtra = new MediaExtra();
    mediaExtra.setId(1);
    mediaExtra.setName("name1");
    mediaExtra.setPrice(BigDecimal.valueOf(1));

    MediaExtra mediaExtra2 = new MediaExtra();
    mediaExtra2.setId(2);
    mediaExtra2.setName("name2");
    mediaExtra2.setPrice(BigDecimal.valueOf(2));

    List<MediaExtra> expectedMediaExtra = new ArrayList<>();
    expectedMediaExtra.add(mediaExtra);
    expectedMediaExtra.add(mediaExtra2);

    Pageable pageable = PageRequest.of(1, 2);
    Page<MediaExtra> expectedPage =
        new PageImpl<>(expectedMediaExtra, pageable, expectedMediaExtra.size());

    doReturn(expectedPage).when(mediaExtraRepository).findAll(pageable);
    PagedResult<MediaExtraDto> actual = mediaExtraService.getAll(pageable);

    assertNotNull(actual);
    assertEquals(
        mapper.convertToMediaExtraDtoList(expectedPage.getContent()), actual.getElements());
    assertEquals(expectedPage.getContent().get(0), mediaExtra);
    assertEquals(expectedPage.getTotalPages(), actual.getNumOfTotalPages());
  }
}
