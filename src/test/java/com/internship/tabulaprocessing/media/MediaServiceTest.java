package com.internship.tabulaprocessing.media;

import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import com.internship.tabulaprocessing.repository.MediaRepository;
import com.internship.tabulaprocessing.service.MediaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

    @Mock
    private MediaRepository mediaRepository;
    @Mock
    private MediaExtraRepository mediaExtraRepository;

    @InjectMocks
    private MediaService mediaService;


    @Test
    void getOne() {
        when(mediaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaService.getOne(Mockito.anyInt()));
    }

    @Test
    void create (){

        MediaDto mediaDto = new MediaDto();
        mediaDto.setId(1);
        List<String> extraIds=  new ArrayList<>();
        extraIds.add("2");
        mediaDto.setMediaExtraIds(extraIds);
        when(mediaExtraRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaService.create(mediaDto));
    }

    @Test
    void deleteById() {
        Media media = new Media();
        media.setId(1);
        when(mediaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> mediaService.deleteById(media.getId()));
    }

    @Test
    void update() {
        MediaDto mediaDto = new MediaDto();
        mediaDto.setId(5);
        when(mediaRepository.findById(5)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaService.update(5, mediaDto));
    }

}

