package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.controller.QueryParameter;
import com.internship.tabulaprocessing.dto.EmployeeResponseDto;
import com.internship.tabulaprocessing.dto.MediaExtraDto;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.PagedResult;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaExtraService {

    private final MediaExtraRepository mediaExtraRepository;

    private final Mapper mapper;

    public PagedResult<MediaExtraDto> getAll(QueryParameter queryParameter){

        Page<MediaExtra> page = mediaExtraRepository.findAll(queryParameter.getPageable());
        Page<MediaExtraDto> dtoPage = page.map(entity -> {
              return mapper.convertToMediaExtraDTO(entity);
        });
        return new PagedResult<MediaExtraDto>(
                dtoPage.toList(),
                page.getNumber(),
                page.getTotalPages());
    }

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

    public ResponseEntity<?> deleteById(int id) {
        try {
            mediaExtraRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format("Media Extra with id of %s was not found!", id));
        }

        return ResponseEntity.ok(String.format("Media Extra with id of %s was deleted successfully!", id));
    }


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