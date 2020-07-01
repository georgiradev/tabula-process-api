package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.MediaExtraDto;
import com.internship.tabulaprocessing.dto.MediaExtraRequestDto;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.MediaExtraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/media_extras")
public class MediaExtraController {

  private final MediaExtraService mediaExtraService;
  private final Mapper mapper;
  private final PatchMapper patchMapper;

  public MediaExtraController(MediaExtraService mediaExtraService, Mapper mapper, PatchMapper patchMapper) {
    this.mediaExtraService = mediaExtraService;
    this.mapper = mapper;
    this.patchMapper = patchMapper;
  }

  @GetMapping
  public PagedResult<MediaExtraDto> getAll(QueryParameter queryParameter) {
    return mediaExtraService.getAll(queryParameter.getPageable());
  }

  @PostMapping
  public ResponseEntity<MediaExtraDto> create(@Valid @RequestBody MediaExtraRequestDto mediaExtraRequestDto) {
    return mediaExtraService.create(mapper.convertToMediaExtraEntity(mediaExtraRequestDto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MediaExtraDto> getOne(@PathVariable String id) {

    int num = Integer.parseInt(id);
    return ResponseEntity.ok(mapper.convertToMediaExtraDTO(mediaExtraService.getOne(num)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {

    int num = Integer.parseInt(id);
    return mediaExtraService.deleteById(num);
  }

  @PatchMapping(path = "/{id}", consumes = {"application/merge-patch+json"})
  public ResponseEntity<MediaExtraDto> patch(
          @PathVariable int id,  @RequestBody MediaExtraRequestDto mediaExtraRequestDto) {

    MediaExtra mediaExtra = mediaExtraService.getOne(id);
    MediaExtra patched = patchMapper.mapObjectsToMediaExtra(mediaExtraRequestDto, mediaExtra);

    return ResponseEntity.ok(
            mapper.convertToMediaExtraDTO(mediaExtraService.update(id, patched)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<MediaExtraDto> update(
          @PathVariable int id, @Valid @RequestBody MediaExtraRequestDto mediaExtraRequestDto) {

    MediaExtra mediaExtra = mediaExtraService.getOne(id);

    return ResponseEntity.ok(
            mapper.convertToMediaExtraDTO(mediaExtraService.update(id,
                    mapper.convertToMediaExtraEntity(mediaExtraRequestDto))));
  }

}
