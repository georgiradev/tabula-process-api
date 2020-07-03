package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.WorkLogCreateRequest;
import com.internship.tabulaprocessing.dto.WorkLogPutRequest;
import com.internship.tabulaprocessing.dto.WorkLogResponse;
import com.internship.tabulaprocessing.dto.WorkLogPatchRequest;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.WorkLog;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.WorkLogService;
import com.internship.tabulaprocessing.service.WorkLogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/worklogs")
@RequiredArgsConstructor
public class WorkLogController {
  private final WorkLogServiceImpl workLogService;
  private final PatchMapper patchMapper;
  private final Mapper mapper;

  @GetMapping
  public ResponseEntity<PagedResult<WorkLogResponse>> getByPage(@Valid QueryParameter param) {
    PagedResult pagedResult = workLogService.getByPage(param.getPageable());
    pagedResult.setElements(mapper.convertToWorkLogResponse(pagedResult.getElements()));
    return ResponseEntity.ok(pagedResult);
  }

  @PostMapping
  public ResponseEntity<WorkLogResponse> create(@Valid @RequestBody WorkLogCreateRequest workLogDto) {
    WorkLog workLog = mapper.convertToWorkLogEntity(workLogDto);
    return ResponseEntity.ok(mapper.convertToWorkLogResponse(workLogService.create(workLog)));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<WorkLogResponse> getOne(@Valid @PathVariable int id) {
    return ResponseEntity.ok(mapper.convertToWorkLogResponse(workLogService.findById(id)));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<WorkLogResponse> update(@Valid @PathVariable int id,
                                                @Valid @RequestBody WorkLogPutRequest workLogDto) {
    WorkLog workLog = mapper.convertToWorkLogEntity(workLogDto);
    WorkLog updatedWorkLog = workLogService.update(workLog,id);
    return ResponseEntity.ok(mapper.convertToWorkLogResponse(updatedWorkLog));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<String> delete(@Valid @PathVariable Integer id) {
    workLogService.delete(id);
    return ResponseEntity.ok(String.format("WorkLog with id of %s is deleted!", id));
  }

  @PatchMapping(path = "/{id}",
                consumes = {"application/merge-patch+json"})
  public ResponseEntity<WorkLogResponse> patch(@PathVariable int id,
                                               @Valid @RequestBody WorkLogPatchRequest dto) {
    WorkLog workLog = workLogService.findById(id);
    WorkLog patchedWorkLog = patchMapper.mapObjectsToWorkLog(dto, workLog);
    return ResponseEntity.ok(mapper.convertToWorkLogResponse(workLogService.updatePatch(patchedWorkLog, id)));
  }
}
