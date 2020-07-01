package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.dto.MediaRequestDto;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/media")
public class MediaController {

  private MediaService mediaService;
  private final Mapper mapper;
  private final PatchMapper patchMapper;

  public MediaController(MediaService mediaService, Mapper mapper, PatchMapper patchMapper) {
    this.mediaService = mediaService;
    this.mapper = mapper;
    this.patchMapper = patchMapper;
  }

  @GetMapping
  public PagedResult<MediaDto> getAll(QueryParameter queryParameter) {
    return mediaService.getAll(queryParameter.getPageable());
  }

  @PostMapping
  public ResponseEntity<MediaDto> create(@Valid @RequestBody MediaRequestDto mediaRequestDto) {
    return ResponseEntity.ok(mediaService.create( mediaRequestDto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MediaDto> getOne(@PathVariable String id) {

    int num = Integer.parseInt(id);
    return ResponseEntity.ok(mediaService.getOne(num));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {

    int num = Integer.parseInt(id);
    return mediaService.deleteById(num);
  }

  @PatchMapping(path = "/{id}", consumes = {"application/merge-patch+json"})
  public ResponseEntity<MediaDto> patch(
          @PathVariable int id,  @RequestBody MediaRequestDto mediaRequestDto) {

    return  ResponseEntity.ok(mediaService.patch(id, mediaRequestDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<MediaDto> update(
          @PathVariable int id, @Valid @RequestBody MediaRequestDto mediaRequestDto) {

    return ResponseEntity.ok(mediaService.update(id, mediaRequestDto));
  }

}
