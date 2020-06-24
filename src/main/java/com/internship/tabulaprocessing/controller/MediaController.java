package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.service.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/media")
public class MediaController {

    private MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping
    public PagedResult<MediaDto> getAll(QueryParameter queryParameter) {
        return  mediaService.getAll(queryParameter.getPageable());
    }

    @PostMapping
    public ResponseEntity<MediaDto> create(@Valid @RequestBody MediaDto mediaDto) {
        return ResponseEntity.ok(mediaService.create(mediaDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaDto> getOne(@PathVariable String id) {

        int num = Integer.parseInt(id);
        return ResponseEntity.ok(mediaService.getOne(num));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)  {

        int num = Integer.parseInt(id);
        return mediaService.deleteById(num);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaDto> update(@PathVariable String id,
                                           @Valid @RequestBody MediaDto mediaDto) {
        int num = Integer.parseInt(id);
        return mediaService.update(num, mediaDto);
    }
}