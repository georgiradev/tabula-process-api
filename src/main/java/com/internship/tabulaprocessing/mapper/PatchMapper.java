package com.internship.tabulaprocessing.mapper;


import com.internship.tabulaprocessing.dto.*;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.entity.*;
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

    Process mapObjectsToProcess(
            ProcessRequestDto data, @MappingTarget Process process);

}
