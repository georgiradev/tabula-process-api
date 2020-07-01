package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.dto.MediaRequestDto;
import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
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
  private final PatchMapper patchMapper;

  public PagedResult<MediaDto> getAll(Pageable pageable) {
    Page<Media> medias = mediaRepository.findAll(pageable);
    return new PagedResult<>(
        getMediaDtoList(medias), pageable.getPageNumber() + 1,
            medias.getTotalPages(), medias.getTotalElements());
  }

  private List<MediaDto> getMediaDtoList(Page<Media> mediaPage) {
    Page<MediaDto> dtoPage =
        mediaPage.map(
            entity -> { MediaDto mediaDto = mapper.convertToMediaDTO(entity);

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
    Media media = findById(id);

    List<String> extras = new ArrayList<>();
    for (MediaExtra mediaExtra : media.getMediaExtras()) {
      extras.add(String.valueOf(mediaExtra.getId()));
    }

    MediaDto resultMediaDto = mapper.convertToMediaDTO(media);
    resultMediaDto.setMediaExtraIds(extras);
    return resultMediaDto;
  }

  public MediaDto create(MediaRequestDto mediaRequestDto) {
    isAlreadyExisting(0, mediaRequestDto.getName());
    Media media = mapper.convertToMediaEntity( mediaRequestDto);
    List<String> existingIds = new ArrayList<>();

    if (mediaRequestDto.getMediaExtraIds() != null) {
      Set<MediaExtra> mediaExtraSet = new HashSet<>();
      List<String> ids = mediaRequestDto.getMediaExtraIds();

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

  public MediaDto update(int id, MediaRequestDto mediaRequestDto) {

    Media media = findById(id);
    isAlreadyExisting(id, mediaRequestDto.getName());

    List<String> ids = mediaRequestDto.getMediaExtraIds();
    List<OrderItem> orderItems = media.getOrderItems();

    // Find the MediaExtras
    Set<MediaExtra> mediaExtraSet = new HashSet<>();

    if (mediaRequestDto.getMediaExtraIds() != null) {
      for (String mediaExtraId : mediaRequestDto.getMediaExtraIds()) {
        Optional<MediaExtra> mediaExtra =
            mediaExtraRepository.findById(Integer.parseInt(mediaExtraId));
        if (!mediaExtra.isPresent()) {
          throw new EntityNotFoundException("Media Extra not found.");
        } else {
          mediaExtraSet.add(mediaExtra.get());
        }
      }
    }

    media = mapper.convertToMediaEntity(mediaRequestDto);
    media.setId(id);
    media.setMediaExtras(mediaExtraSet);
    media.setOrderItems(orderItems);
    media.calculatePrice();
    mediaRepository.save(media);
    MediaDto mediaDto = mapper.convertToMediaDTO(media);
    mediaDto.setMediaExtraIds(ids);
    return mediaDto;
  }

  public MediaDto patch(int id, MediaRequestDto mediaRequestDto) {
    Media media = findById(id);

    isAlreadyExisting(id, mediaRequestDto.getName());

    List<String> ids = new ArrayList<>();
    Set<MediaExtra> mediaExtraSet = new HashSet<>();

    Media patchedMedia = patchMapper.mapObjectsToMedia(mediaRequestDto, media);

    if(mediaRequestDto.getMediaExtraIds() != null) {
      media.removeExtrasPrice();
      for(String mediaExtraId : mediaRequestDto.getMediaExtraIds()) {
        Optional<MediaExtra> mediaExtra =
                mediaExtraRepository.findById(Integer.parseInt(mediaExtraId));
        if(!mediaExtra.isPresent()) {
          throw new EntityNotFoundException("Media Extra not found.");
        } else {
          mediaExtraSet.add(mediaExtra.get());
          ids.add(String.valueOf(mediaExtra.get().getId()));
        }
      }
      patchedMedia.setMediaExtras(mediaExtraSet);
      patchedMedia.calculatePrice();
    }
    else {
      mediaExtraSet.addAll(media.getMediaExtras());
      for(MediaExtra mediaExtra: mediaExtraSet){
        ids.add(String.valueOf(mediaExtra.getId()));
      }
    }

    mediaRepository.save(patchedMedia);
    MediaDto mediaDto = mapper.convertToMediaDTO(patchedMedia);
    mediaDto.setMediaExtraIds(ids);
    return mediaDto;
  }

  private Media findById(int id){
    Optional<Media> optional = mediaRepository.findById(id);
    if (!optional.isPresent()) {
      throw new EntityNotFoundException(String.format("Media with id of %s was not found!", id));
    }
    return  optional.get();
  }

  private boolean isAlreadyExisting(int id, String name){
    Optional<Media> optionalByName = mediaRepository.findByName(name);
    if (optionalByName.isPresent() && optionalByName.get().getId()!=id) {
      throw new EntityAlreadyPresentException(String.format("Media with name %s already exists", name));
    }
    return true;
  }
}
