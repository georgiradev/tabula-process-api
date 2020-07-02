package com.internship.tabulaprocessing.mapper;


import com.internship.tabulaprocessing.dto.OrderPatchRequestDTO;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.dto.MediaExtraRequestDto;
import com.internship.tabulaprocessing.dto.MediaRequestDto;
import com.internship.tabulaprocessing.dto.OrderPatchRequestDTO;
import com.internship.tabulaprocessing.dto.TrackingHistoryRequestDTO;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.dto.TimeOffTypeRequestDto;
import com.internship.tabulaprocessing.entity.Media;
import com.internship.tabulaprocessing.entity.MediaExtra;
import com.internship.tabulaprocessing.entity.TimeOffType;
import com.internship.tabulaprocessing.entity.TrackingHistory;
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

    TrackingHistory patchTrackingHistory(TrackingHistoryRequestDTO requestDTO, @MappingTarget TrackingHistory trackingHistory);

    Media mapObjectsToMedia(MediaRequestDto data, @MappingTarget Media media);

    MediaExtra mapObjectsToMediaExtra(MediaExtraRequestDto data, @MappingTarget MediaExtra mediaExtra);

    Process mapObjectsToProcess(
            ProcessRequestDto data, @MappingTarget Process process);

}
