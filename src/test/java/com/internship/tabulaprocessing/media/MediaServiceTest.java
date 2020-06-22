package com.internship.tabulaprocessing.media;

import com.internship.tabulaprocessing.dto.MediaDto;
import com.internship.tabulaprocessing.dto.MediaExtraDto;
import com.internship.tabulaprocessing.entity.Media;
<<<<<<< HEAD:src/test/java/com/internship/tabulaprocessing/media/MediaServiceTest.java
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.provider.ProcessProvider;
=======
>>>>>>> develop:src/test/java/com/internship/tabulaprocessing/media/service/MediaServiceTest.java
import com.internship.tabulaprocessing.repository.MediaExtraRepository;
import com.internship.tabulaprocessing.repository.MediaRepository;
import com.internship.tabulaprocessing.service.MediaExtraService;
import com.internship.tabulaprocessing.service.MediaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

    @Mock
    private MediaRepository mediaRepository;
    @Mock
    private MediaExtraRepository mediaExtraRepository;

    @InjectMocks
    private MediaService mediaService;


<<<<<<< HEAD:src/test/java/com/internship/tabulaprocessing/media/MediaServiceTest.java
=======
        when(mediaRepository.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(media, mediaService.getAll(anyInt()).getBody());
    }
    
    @Test
    void create (){

        MediaDto mediaDto = new MediaDto();
        mediaDto.setId(1);
        String[] extraIds=  new String[2];
        extraIds[0]="3";
        extraIds[1]="2";
        mediaDto.setMediaExtraIds(extraIds);
        when(mediaExtraRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaService.create(mediaDto));
    }
>>>>>>> develop:src/test/java/com/internship/tabulaprocessing/media/service/MediaServiceTest.java

    @Test
    void getOne() {
        when(mediaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> mediaService.getOne(Mockito.anyInt()));
    }

    @Test
<<<<<<< HEAD:src/test/java/com/internship/tabulaprocessing/media/MediaServiceTest.java
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
=======
>>>>>>> develop:src/test/java/com/internship/tabulaprocessing/media/service/MediaServiceTest.java
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
<<<<<<< HEAD:src/test/java/com/internship/tabulaprocessing/media/MediaServiceTest.java

}
=======
}
>>>>>>> develop:src/test/java/com/internship/tabulaprocessing/media/service/MediaServiceTest.java
