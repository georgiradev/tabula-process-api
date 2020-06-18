package com.internship.tabulaprocessing.service;

import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import com.internship.tabulaprocessing.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final MediaExtraRepository mediaExtraRepository;
    private final Mapper mapper;

    public ResponseEntity<List<Media>> getAll(int num){
        Pageable pageable = PageRequest.of(num, 10);
        Page<Media> page = mediaRepository.findAll(pageable);
        List<MediaDto> mediaDtoList = new ArrayList<>();
        for(Media media:page.toList()){
            mediaDtoList.add(mapper.convertToMediaDTO(media));
        }
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
        Set<MediaExtra> mediaExtraSet = new HashSet<>();
        String[] ids = mediaDto.getMediaExtraIds();
        for(String mediaExtraId: mediaDto.getMediaExtraIds()){
            Optional<MediaExtra> mediaExtra = mediaExtraRepository.findById(Integer.parseInt(mediaExtraId));
            if(!mediaExtra.isPresent()){
                throw new EntityNotFoundException("Media Extra not found.");
            }
            else mediaExtraSet.add(mediaExtra.get());
        }

        Media media = mapper.convertToMediaEntity(mediaDto);
        media.setMediaExtras(mediaExtraSet);
        media.calculatePrice();
        mediaRepository.save(media);
        mediaDto = mapper.convertToMediaDTO(media);
        mediaDto.setMediaExtraIds(ids);
        return new ResponseEntity<>(mediaDto, HttpStatus.CREATED);
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
        //Find Media
        Optional<Media> optional = mediaRepository.findById(id);
        if(!optional.isPresent()){
            throw new EntityNotFoundException(String.format("Media with id of %s was not found!", id));
        }
        String[] ids = mediaDto.getMediaExtraIds();
        //Find the MediaExtras
        Set<MediaExtra> mediaExtraSet = new HashSet<>();

        for(String mediaExtraId: mediaDto.getMediaExtraIds()){
            Optional<MediaExtra> mediaExtra = mediaExtraRepository.findById(Integer.parseInt(mediaExtraId));
            if(!mediaExtra.isPresent()){
                throw new EntityNotFoundException("Media Extra not found.");
            }
            else mediaExtraSet.add(mediaExtra.get());
        }

        Media media = mapper.convertToMediaEntity(mediaDto);
        media.setId(id);
        media.setMediaExtras(mediaExtraSet);
        media.calculatePrice();
        mediaRepository.save(media);
        mediaDto = mapper.convertToMediaDTO(media);
        mediaDto.setMediaExtraIds(ids);
        return ResponseEntity.ok(mediaDto);
    }
}

