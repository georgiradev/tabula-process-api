package com.internship.tabulaprocessing.media.controller;

import com.internship.tabulaprocessing.media.dto.MediaDto;
import com.internship.tabulaprocessing.media.model.Media;
import com.internship.tabulaprocessing.media.service.MediaService;
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
    public ResponseEntity<List<Media>> getAll() {
        return  mediaService.getAll();
    }

    @PostMapping
    public ResponseEntity<MediaDto> create(@Valid @RequestBody MediaDto mediaDto) {
        return mediaService.create(mediaDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaDto> getOne(@PathVariable int id) throws Exception {
        return mediaService.getOne(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id)  {
        return mediaService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaDto> update(@PathVariable int id,
                                       @Valid @RequestBody MediaDto mediaDto) throws Exception {
        return mediaService.update(id, mediaDto);
    }

}