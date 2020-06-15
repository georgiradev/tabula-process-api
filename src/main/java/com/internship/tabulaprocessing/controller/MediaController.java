package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.service.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    private MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping
    public ResponseEntity<List<Media>> getAll(@RequestParam(defaultValue = "0") int page) {
        return  mediaService.getAll(page);
    }

    @PostMapping
    public ResponseEntity<MediaDto> create(@Valid @RequestBody MediaDto mediaDto) {
        return mediaService.create(mediaDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaDto> getOne(@PathVariable String id) {

        int num = Integer.parseInt(id);
        return mediaService.getOne(num);
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