package com.internship.tabulaprocessing.media;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.dto.MediaRequestDto;
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
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

  @Mock private MediaRepository mediaRepository;
  @Mock private MediaExtraRepository mediaExtraRepository;

  @InjectMocks private MediaService mediaService;

  @Mock private Mapper mapper;

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

    PagedResult<MediaDto> employeeDtoPagedResult =
        mediaService.getAll(queryParameter.getPageable());
    assertTrue(employeeDtoPagedResult.getElements().isEmpty());
  }

  @Test
  void create() {
    MediaExtra mediaExtra = MediaExtraProvider.getMediaExtraInstance();

    Media media = MediaProvider.getMediaInstance();
    media.setMediaExtras(Collections.singleton(mediaExtra));

    MediaDto mediaDto = new MediaDto();

    mediaDto.setMediaExtraIds(
        media.getMediaExtras().stream()
            .map(mediaExtra1 -> String.valueOf(mediaExtra1.getId()))
            .collect(Collectors.toList()));

    mediaDto.setId(media.getId());
    mediaDto.setName(media.getName());
    mediaDto.setPrice(media.getPrice());

    when(mapper.convertToMediaDTO(any())).thenReturn(mediaDto);
    when(mediaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(media));


    MediaDto actualMedia = mediaService.getOne(1);

    assertEquals(media.getId(), actualMedia.getId());
    assertEquals(media.getName(), actualMedia.getName());

    assertThrows(RuntimeException.class, () -> mediaService.create( new MediaRequestDto()));
  }

  @Test
  void DeleteById() {
    mediaService.deleteById(89);
    verify(mediaRepository, times(1)).deleteById(eq(89));
  }

  @Test
  void update() {
    MediaRequestDto mediaDto = new MediaRequestDto();
    when(mediaRepository.findById(anyInt())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> mediaService.update(5, mediaDto));

    assertThrows(RuntimeException.class, () -> mediaService.update( anyInt(), new MediaRequestDto()));
  }

  @Test
  void updateIfMediaAlreadyPresent() {

    Media media = MediaProvider.getMediaInstance();
    when(mediaRepository.findById(anyInt())).thenReturn(Optional.of(media));

    Media media1 = new Media();
    media1.setId(90);
    media1.setName(media.getName());

    Mockito.when(mediaRepository.findByName(anyString())).thenReturn(Optional.of(media1));
    assertThrows(RuntimeException.class, () -> mediaService.update( 1,  new MediaRequestDto()));
  }

  @Test
  void patch() {
    MediaRequestDto mediaDto = new MediaRequestDto();
    when(mediaRepository.findById(anyInt())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> mediaService.patch(5, mediaDto));
    assertThrows(RuntimeException.class, () -> mediaService.patch( anyInt(), new MediaRequestDto()));
  }

  @Test
  void CreateIfExtraNotFound(){
    MediaRequestDto mediaRequestDto = new MediaRequestDto();
    mediaRequestDto.setPrice(BigDecimal.valueOf(34));
    mediaRequestDto.setName("yolo");
    List<String> ids = new ArrayList<>();
    ids.add("49");
    mediaRequestDto.setMediaExtraIds(ids);
    when(mediaExtraRepository.findById(anyInt())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, ()->mediaService.create(mediaRequestDto));
  }

}
