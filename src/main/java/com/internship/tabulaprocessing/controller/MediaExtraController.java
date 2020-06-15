package com.internship.tabulaprocessing.controller;

import com.internship.tabulaprocessing.dto.MediaExtraDto;
import com.internship.tabulaprocessing.service.MediaExtraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/media_extras")
public class MediaExtraController {

    private MediaExtraService mediaExtraService;

    public MediaExtraController(MediaExtraService mediaExtraService) {
        this.mediaExtraService = mediaExtraService;
    }

    @GetMapping
    public ResponseEntity<List<MediaExtraDto>> getAll(@RequestParam(defaultValue = "0") int page) {
        return  mediaExtraService.getAll(page);
    }

    @PostMapping
    public ResponseEntity<MediaExtraDto> create(@Valid @RequestBody MediaExtraDto mediaExtraDto) {
        return mediaExtraService.create(mediaExtraDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaExtraDto> getOne(@PathVariable String id) {

        int num = Integer.parseInt(id);
        return mediaExtraService.getOne(num);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)  {

        int num = Integer.parseInt(id);
        return mediaExtraService.deleteById(num);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaExtraDto> update(@PathVariable String id,
                                           @Valid @RequestBody MediaExtraDto mediaExtraDto) {
        int num = Integer.parseInt(id);
        return mediaExtraService.update(num, mediaExtraDto);
    }

}