package com.internship.tabulaprocessing.mapper;

<<<<<<< HEAD
import com.internship.tabulaprocessing.dto.OrderPatchRequestDTO;
import com.internship.tabulaprocessing.entity.Order;
=======
>>>>>>> 7e41505b9d822b6f38130339c23146590529a55b
import com.internship.tabulaprocessing.dto.MediaExtraRequestDto;
import com.internship.tabulaprocessing.dto.MediaRequestDto;
import com.internship.tabulaprocessing.dto.TimeOffTypeRequestDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.TimeOffType;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
        (componentModel = "spring",
                nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatchMapper {

    PatchMapper INSTANCE =  Mappers.getMapper(PatchMapper.class);

    Order patchOrder(OrderPatchRequestDTO dto, @MappingTarget Order order);

    TimeOffType mapObjectsToTimeOffType(
            TimeOffTypeRequestDto data, @MappingTarget TimeOffType timeOffType);

    Media mapObjectsToMedia(MediaRequestDto data, @MappingTarget Media media);

    MediaExtra mapObjectsToMediaExtra(MediaExtraRequestDto data, @MappingTarget MediaExtra mediaExtra);
}
