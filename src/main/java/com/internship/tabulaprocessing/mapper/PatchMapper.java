package com.internship.tabulaprocessing.mapper;

import com.internship.tabulaprocessing.dto.TimeOffTypeRequestDto;
import com.internship.tabulaprocessing.entity.TimeOffType;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
        (componentModel = "spring",
                nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatchMapper {

    PatchMapper INSTANCE =  Mappers.getMapper(PatchMapper.class);

    TimeOffType mapObjectsToTimeOffType(
            TimeOffTypeRequestDto data, @MappingTarget TimeOffType timeOffType);
}
