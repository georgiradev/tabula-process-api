package com.internship.tabulaprocessing.media.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.internship.tabulaprocessing.media.dto.MediaDto;
import com.internship.tabulaprocessing.media.model.Media;
import com.internship.tabulaprocessing.media.repository.MediaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    private MediaRepository mediaRepository;
    ObjectMapper mapper = new ObjectMapper();

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ResponseEntity<List<Media>> getAll(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Media> page = mediaRepository.findAll(pageable);
        return new ResponseEntity<>(page.toList(), HttpStatus.OK);
    }

    public ResponseEntity<MediaDto> getOne(int id) {
        Optional<Media> media = mediaRepository.findById(id);
        if(!media.isPresent()){
            throw new EntityNotFoundException("Media not found.");
        }
        MediaDto mediaDto = mapper.convertValue(media.get(), MediaDto.class);
        return  ResponseEntity.ok(mediaDto);
    }

    public ResponseEntity<MediaDto> create(MediaDto mediaDto) {

        Media media = mapper.convertValue(mediaDto, Media.class);
        mediaRepository.save(media);
        mediaDto.setId(media.getId());

        return  new ResponseEntity<>(mediaDto, HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteById(int id) {

        Optional<Media> optional = mediaRepository.findById(id);

        if(!optional.isPresent()){
            throw new EntityNotFoundException(String.format("Media with id of %s was not found!", id));
        }

        mediaRepository.deleteById(id);
        return ResponseEntity.ok(String.format("Media with id of %s was deleted successfully!", id));
    }

    public ResponseEntity<MediaDto> update(int id, MediaDto mediaDto)  {
        Optional<Media> optional = mediaRepository.findById(id);

        if(!optional.isPresent()){
            throw new EntityNotFoundException(String.format("Media with id of %s was not found!", id));
        }

        Media media = mapper.convertValue(mediaDto, Media.class);
        media.setId(id);
        mediaRepository.save(media);

        return ResponseEntity.ok(mediaDto);
    }
}

