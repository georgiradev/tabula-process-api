package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.mapper.MapperImpl;
import com.internship.tabulaprocessing.repository.MediaRepository;
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
    private Mapper mapper;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
        mapper = new MapperImpl();
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

        MediaDto mediaDto = mapper.convertToMediaDTO(media.get());
        return  ResponseEntity.ok(mediaDto);
    }

    public ResponseEntity<MediaDto> create(MediaDto mediaDto) {
        Media media = mapper.convertToMediaEntity(mediaDto);
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

        Media media = mapper.convertToMediaEntity(mediaDto);
        media.setId(id);
        mediaRepository.save(media);
        mediaDto = mapper.convertToMediaDTO(media);
        return ResponseEntity.ok(mediaDto);
    }
}

