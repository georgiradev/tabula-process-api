package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.dto.MediaExtraDto;
import com.internship.tabulaprocessing.dto.MediaExtraRequestDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.exception.EntityAlreadyPresentException;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaExtraService {
  private final MediaExtraRepository mediaExtraRepository;

  private final Mapper mapper;

  public PagedResult<MediaExtraDto> getAll(Pageable pageable) {
    Page<MediaExtra> medias = mediaExtraRepository.findAll(pageable);
    return new PagedResult<>(
        mapper.convertToMediaExtraDtoList(medias.toList()),
        pageable.getPageNumber() + 1,
        medias.getTotalPages(),
        medias.getTotalElements());
  }

  public MediaExtra getOne(int id) {
    Optional<MediaExtra> mediaExtra = mediaExtraRepository.findById(id);

    if (!mediaExtra.isPresent()) {
      throw new EntityNotFoundException("Media Extra not found.");
    }
    return mediaExtra.get();
  }

  public ResponseEntity<MediaExtraDto> create(MediaExtra mediaExtra) {
    isAlreadyExisting(0, mediaExtra.getName());
    mediaExtraRepository.save(mediaExtra);

    return new ResponseEntity<>(mapper.convertToMediaExtraDTO(mediaExtra), HttpStatus.CREATED);
  }

  public ResponseEntity<?> deleteById(int id) {
    try {
      mediaExtraRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException(
          String.format("Media Extra with id of %s was not found!", id));
    }

    return ResponseEntity.ok(
        String.format("Media Extra with id of %s was deleted successfully!", id));
  }

  public MediaExtra update(int id, MediaExtra mediaExtra) {

    isAlreadyExisting(id, mediaExtra.getName());
    mediaExtraRepository.save(mediaExtra);
    return mediaExtra;
  }

  private boolean isAlreadyExisting(int id, String name){
    Optional<MediaExtra> optionalByName = mediaExtraRepository.findByName(name);
    if (optionalByName.isPresent() && optionalByName.get().getId()!=id) {
      throw new EntityAlreadyPresentException(String.format("Media Extra with name %s already exists", name));
    }
    return true;
  }
}
