package com.internship.tabulaprocessing.mapper;

import com.internship.tabulaprocessing.dto.TimeOffPatchRequest;
import com.internship.tabulaprocessing.dto.TimeOffPatchStatusRequest;
import com.internship.tabulaprocessing.entity.TimeOff;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatchMapper {

    PatchMapper mapper = Mappers.getMapper(PatchMapper.class);

    TimeOff mapObjectsToTimeOffEntity(TimeOffPatchRequest data, @MappingTarget TimeOff company);

    TimeOff mapObjectsToTimeOffEntity(TimeOffPatchStatusRequest data, @MappingTarget TimeOff company);
}
