package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.dto.MediaExtraDto;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import com.internship.tabulaprocessing.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MediaExtraService {
    private MediaExtraRepository mediaExtraRepository;
    @Autowired
    private Mapper mapper;

    public MediaExtraService(MediaExtraRepository mediaExtraRepository) {
        this.mediaExtraRepository = mediaExtraRepository;
    }

    @Cacheable(value="MediaExtras")
    public ResponseEntity<List<MediaExtraDto>> getAll(int num){
        Pageable pageable = PageRequest.of(num, 10);
        Page<MediaExtra> page = mediaExtraRepository.findAll(pageable);
        List<MediaExtraDto> mediaExtraDtoList = new ArrayList<>();
        for(MediaExtra mediaExtra:page.toList()){
            mediaExtraDtoList.add(mapper.convertToMediaExtraDTO(mediaExtra));
        }
        return new ResponseEntity<>(mediaExtraDtoList, HttpStatus.OK);
    }

    @Cacheable(value="MediaExtras",key="#id")
    public ResponseEntity<MediaExtraDto> getOne(int id) {
        Optional<MediaExtra> mediaExtra = mediaExtraRepository.findById(id);

        if(!mediaExtra.isPresent()){
            throw new EntityNotFoundException("Media Extra not found.");
        }

        MediaExtraDto mediaExtraDto = mapper.convertToMediaExtraDTO(mediaExtra.get());
        return  ResponseEntity.ok(mediaExtraDto);
    }

    public ResponseEntity<MediaExtraDto> create(MediaExtraDto mediaExtraDto) {
        MediaExtra mediaExtra = mapper.convertToMediaExtraEntity(mediaExtraDto);
        mediaExtraRepository.save(mediaExtra);
        mediaExtraDto = mapper.convertToMediaExtraDTO(mediaExtra);
        return new ResponseEntity<>(mediaExtraDto, HttpStatus.CREATED);
    }

    @CacheEvict(value="MediaExtras",key="#id")
    public ResponseEntity<?> deleteById(int id) {

        Optional<MediaExtra> optional = mediaExtraRepository.findById(id);

        if(!optional.isPresent()){
            throw new EntityNotFoundException(String.format("Media Extra with id of %s was not found!", id));
        }

        mediaExtraRepository.deleteById(id);
        return ResponseEntity.ok(String.format("Media Extra with id of %s was deleted successfully!", id));
    }

    @CachePut(value="MediaExtras",key="#id")
    public ResponseEntity<MediaExtraDto> update(int id, MediaExtraDto mediaExtraDto)  {
        Optional<MediaExtra> optional = mediaExtraRepository.findById(id);

        if(!optional.isPresent()){
            throw new EntityNotFoundException(String.format("Media Extra with id of %s was not found!", id));
        }
        optional.get().setId(id);
        optional.get().setName(mediaExtraDto.getName());
        optional.get().setPrice(mediaExtraDto.getPrice());
        mediaExtraRepository.save(optional.get());

        return ResponseEntity.ok(mapper.convertToMediaExtraDTO(optional.get()));
    }
}