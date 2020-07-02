package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.TrackingHistoryRequestDTO;
import com.internship.tabulaprocessing.dto.TrackingHistoryResponseDTO;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.entity.TrackingHistory;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.PatchMapper;
import com.internship.tabulaprocessing.service.TrackingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tracking_history")
public class TrackingHistoryController {

  private TrackingHistoryService service;
  private Mapper mapper;
  private PatchMapper patchMapper;

  @Autowired
  public TrackingHistoryController(
      TrackingHistoryService service, Mapper mapper, PatchMapper patchMapper) {
    this.service = service;
    this.mapper = mapper;
    this.patchMapper = patchMapper;
  }

  @GetMapping
  public ResponseEntity<PagedResult<TrackingHistoryResponseDTO>> findAll(
      @Valid QueryParameter queryParameter, @RequestParam(required = false) Boolean assigned) {

    Page<TrackingHistory> page;

    if (assigned != null) {
      page =
          assigned
              ? service.findAllAssigned(queryParameter.getPageable())
              : service.findAllUnsaigned(queryParameter.getPageable());
    } else {
      page = service.findAll(queryParameter.getPageable());
    }

    List<TrackingHistoryResponseDTO> resultList =
        page.stream()
            .map(trackingHistory -> mapper.convertToTrackingHistoryDTO(trackingHistory))
            .collect(Collectors.toList());

    return ResponseEntity.ok(
        new PagedResult<>(
            resultList, queryParameter.getPage(), page.getTotalPages(), page.getTotalElements()));
  }

  @GetMapping("/order/{id}")
  public ResponseEntity<PagedResult<TrackingHistoryResponseDTO>> findByOrderId(
      @PathVariable int id, @Valid QueryParameter parameter) {

    Page<TrackingHistory> page = service.findAllByOrderId(parameter.getPageable(),id);
    List<TrackingHistoryResponseDTO> resultList =
            page.stream()
                    .map(trackingHistory -> mapper.convertToTrackingHistoryDTO(trackingHistory))
                    .collect(Collectors.toList());

    return ResponseEntity.ok(new PagedResult<>(resultList,parameter.getPage(),page.getTotalPages(),page.getTotalElements()));

  }

  @GetMapping("/{id}")
  public ResponseEntity<TrackingHistoryResponseDTO> findSingle(@PathVariable int id) {
    return ResponseEntity.ok(mapper.convertToTrackingHistoryDTO(service.findById(id)));
  }

  @PutMapping("/{id")
  public ResponseEntity<TrackingHistoryResponseDTO> update(
      @PathVariable int id, @Valid @RequestBody TrackingHistoryRequestDTO requestDTO) {

    TrackingHistory updatedTrackingHistory = mapper.convertToTrackingHistoryEntity(requestDTO);
    updatedTrackingHistory = service.update(updatedTrackingHistory, id);

    return ResponseEntity.ok(mapper.convertToTrackingHistoryDTO(updatedTrackingHistory));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<TrackingHistoryResponseDTO> patch(
      @PathVariable int id, @Valid @RequestBody TrackingHistoryRequestDTO requestDTO) {

    TrackingHistory trackingHistory = service.findById(id);
    TrackingHistory patchedTrackingHistory =
        patchMapper.patchTrackingHistory(requestDTO, trackingHistory);

    return ResponseEntity.ok(
        mapper.convertToTrackingHistoryDTO(service.patch(patchedTrackingHistory)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable int id) {

    service.delete(id);

    return ResponseEntity.ok(
        String.format("TrackingHistory with id %s, successfully deleted.", id));
  }
}
