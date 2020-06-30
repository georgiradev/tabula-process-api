package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.TimeOffTypeRequestDto;
import com.internship.tabulaprocessing.dto.TimeOffTypeResponseDto;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.TimeOffType;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.TimeOffTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/time_off_type")
public class TimeOffTypeController {
  private TimeOffTypeService timeOffTypeService;

  private Mapper mapper;

  private PatchMapper patchMapper;

  @Autowired
  public TimeOffTypeController(
      TimeOffTypeService timeOffTypeService, Mapper mapper, PatchMapper patchMapper) {
    this.timeOffTypeService = timeOffTypeService;
    this.mapper = mapper;
    this.patchMapper = patchMapper;
  }

  @GetMapping("/{id}")
  public HttpEntity get(@PathVariable(name = "id") Integer id) {
    TimeOffType timeOffType = timeOffTypeService.getOneById(id);
    return ResponseEntity.ok(mapper.entityToTimeOffTypeResponseDto(timeOffType));
  }

  @GetMapping
  public ResponseEntity<PagedResult<TimeOffTypeResponseDto>> getAll(
      @Valid QueryParameter queryParameter) {

    Page<TimeOffType> pagedTypes = timeOffTypeService.findAll(queryParameter.getPageable());
    Page<TimeOffTypeResponseDto> responseDtos =
        pagedTypes.map(mapper::entityToTimeOffTypeResponseDto);

    return ResponseEntity.ok(
        new PagedResult<>(
            responseDtos.toList(), queryParameter.getPage(),
                responseDtos.getTotalPages(), pagedTypes.getTotalElements()));
  }

  @PostMapping
  public HttpEntity create(@Valid @RequestBody TimeOffTypeRequestDto timeOffTypeRequestDto) {

    TimeOffType timeOffType = mapper.timeOffTypeRequestDtoToEntity(timeOffTypeRequestDto);
    TimeOffType createdTimeOffType = timeOffTypeService.create(timeOffType);
    return ResponseEntity.ok(mapper.entityToTimeOffTypeResponseDto(createdTimeOffType));
  }

  @PutMapping("/{id}")
  public HttpEntity update(
      @PathVariable("id") Integer id,
      @Valid @RequestBody TimeOffTypeRequestDto timeOffTypeRequestDto) {

    TimeOffType timeOffType = mapper.timeOffTypeRequestDtoToEntity(timeOffTypeRequestDto);
    TimeOffType updatedTimeOffType = timeOffTypeService.update(timeOffType, id);
    return ResponseEntity.ok(mapper.entityToTimeOffTypeResponseDto(updatedTimeOffType));
  }

  @DeleteMapping("/{id}")
  public HttpEntity delete(@PathVariable("id") Integer id) {

    timeOffTypeService.delete(id);
    return ResponseEntity.ok("Deleted successfully");
  }

  @PatchMapping(
      path = "/{id}",
      consumes = {"application/merge-patch+json"})
  public ResponseEntity patch(
      @PathVariable int id, @RequestBody TimeOffTypeRequestDto timeOffTypeRequestDto) {

    TimeOffType timeOffType = timeOffTypeService.getOneById(id);
    TimeOffType patched = patchMapper.mapObjectsToTimeOffType(timeOffTypeRequestDto, timeOffType);
    return ResponseEntity.ok(timeOffTypeService.update(patched, id));
  }
}
