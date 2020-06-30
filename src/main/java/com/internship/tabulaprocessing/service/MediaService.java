package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.OrderItem;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import com.internship.tabulaprocessing.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaService {

  private final MediaRepository mediaRepository;
  private final MediaExtraRepository mediaExtraRepository;
  private final Mapper mapper;

  public PagedResult<MediaDto> getAll(Pageable pageable) {
    Page<Media> medias = mediaRepository.findAll(pageable);
    return new PagedResult<>(
        getMediaDtoList(medias), pageable.getPageNumber() + 1,
            medias.getTotalPages(), medias.getTotalElements());
  }

  private List<MediaDto> getMediaDtoList(Page<Media> mediaPage) {
    Page<MediaDto> dtoPage =
        mediaPage.map(
            entity -> {
              MediaDto mediaDto = mapper.convertToMediaDTO(entity);

              if (entity.getMediaExtras() != null) {
                List<String> extras =
                    entity.getMediaExtras().stream()
                        .map(mediaExtra -> String.valueOf(mediaExtra.getId()))
                        .collect(Collectors.toList());
                mediaDto.setMediaExtraIds(extras);
              }
              return mediaDto;
            });
    return dtoPage.toList();
  }

  public MediaDto getOne(int id) {
    Optional<Media> media = mediaRepository.findById(id);

    if (!media.isPresent()) {
      throw new EntityNotFoundException("Media not found.");
    }

    List<String> extras = new ArrayList<>();
    for (MediaExtra mediaExtra : media.get().getMediaExtras()) {
      extras.add(String.valueOf(mediaExtra.getId()));
    }

    MediaDto resultMediaDto = mapper.convertToMediaDTO(media.get());
    resultMediaDto.setMediaExtraIds(extras);
    return resultMediaDto;
  }

  public MediaDto create(MediaDto mediaDto) {
    Media media = mapper.convertToMediaEntity(mediaDto);
    List<String> existingIds = new ArrayList<>();

    if (mediaDto.getMediaExtraIds() != null) {
      Set<MediaExtra> mediaExtraSet = new HashSet<>();
      List<String> ids = mediaDto.getMediaExtraIds();

      for (String mediaExtraId : ids) {
        Optional<MediaExtra> mediaExtra =
            mediaExtraRepository.findById(Integer.parseInt(mediaExtraId));

        if (!mediaExtra.isPresent()) {
          throw new EntityNotFoundException("Media Extra not found.");
        } else {
          mediaExtraSet.add(mediaExtra.get());
          existingIds.add(mediaExtraId);
        }
      }
      media.setMediaExtras(mediaExtraSet);
    } else {
      media.setMediaExtras(null);
    }

    media.calculatePrice();
    mediaRepository.save(media);
    MediaDto resultMediaDto = mapper.convertToMediaDTO(media);
    resultMediaDto.setMediaExtraIds(existingIds);

    return resultMediaDto;
  }

  public ResponseEntity<?> deleteById(int id) {

    try {
      mediaRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException(String.format("Media  with id of %s was not found!", id));
    }

    return ResponseEntity.ok(String.format("Media with id of %s was deleted successfully!", id));
  }

  public ResponseEntity<MediaDto> update(int id, MediaDto mediaDto) {
    // Find Media
    Optional<Media> optional = mediaRepository.findById(id);
    if (!optional.isPresent()) {
      throw new EntityNotFoundException(String.format("Media with id of %s was not found!", id));
    }
    List<String> ids = mediaDto.getMediaExtraIds();
    List<OrderItem> orderItems = optional.get().getOrderItems();
    // Find the MediaExtras
    Set<MediaExtra> mediaExtraSet = new HashSet<>();

    if (mediaDto.getMediaExtraIds() != null) {
      for (String mediaExtraId : mediaDto.getMediaExtraIds()) {
        Optional<MediaExtra> mediaExtra =
            mediaExtraRepository.findById(Integer.parseInt(mediaExtraId));
        if (!mediaExtra.isPresent()) {
          throw new EntityNotFoundException("Media Extra not found.");
        } else {
          mediaExtraSet.add(mediaExtra.get());
        }
      }
    }
    Media media = mapper.convertToMediaEntity(mediaDto);
    media.setId(id);
    media.setMediaExtras(mediaExtraSet);
    media.setOrderItems(orderItems);
    media.calculatePrice();
    mediaRepository.save(media);
    mediaDto = mapper.convertToMediaDTO(media);
    mediaDto.setMediaExtraIds(ids);
    return ResponseEntity.ok(mediaDto);
  }
}
