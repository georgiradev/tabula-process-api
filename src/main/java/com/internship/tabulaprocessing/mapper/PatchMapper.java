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
import com.internship.tabulaprocessing.entity.*;
import com.internship.tabulaprocessing.dto.OrderPatchRequestDTO;
import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.entity.Process;
import com.internship.tabulaprocessing.service.EmployeeService;
import com.internship.tabulaprocessing.service.TimeOffTypeService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
@org.mapstruct.Mapper
        (componentModel = "spring",
         uses = {EmployeeService.class, Mapper.class},
         nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PatchMapper {

    @Autowired
    Mapper mapper;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TimeOffTypeService timeOffTypeService;

    public abstract TimeOffType mapObjectsToTimeOffType(TimeOffTypeRequestDto data,
                                                        @MappingTarget TimeOffType timeOffType);

    public abstract TimeOff mapObjectsToTimeOffEntity(TimeOffPatchStatusRequest data,
                                                      @MappingTarget TimeOff timeOff);

    public TimeOff mapObjectsToTimeOffEntity(TimeOffPatchRequest data, TimeOff timeOff) {
        if ( data == null ) {
            return null;
        }

        if ( data.getStartDateTime() != null ) {
            timeOff.setStartDateTime( data.getStartDateTime() );
        }

        if ( data.getEndDateTime() != null ) {
            timeOff.setEndDateTime( data.getEndDateTime() );
        }

        if ( data.getComment() != null ) {
            timeOff.setComment( data.getComment() );
        }

        if( data.getApproverId()!=0 ) {
            Employee approver = mapper.convertToEmployeeEntity(employeeService.getOne(data.getApproverId()).getBody());
            timeOff.setApprover(approver);
        }

        if( data.getEmployeeId()!=0 ) {
            Employee employee = mapper.convertToEmployeeEntity(employeeService.getOne(data.getEmployeeId()).getBody());
            timeOff.setEmployee(employee);
        }

        if( data.getTypeOfTimeOffId()!=0 ) {
            TimeOffType timeOffType = timeOffTypeService.getOneById(data.getTypeOfTimeOffId());
            timeOff.setTimeOffType(timeOffType);
        }

        return timeOff;
    }

    public abstract Media mapObjectsToMedia(MediaRequestDto data, @MappingTarget Media media);

    public abstract MediaExtra mapObjectsToMediaExtra(MediaExtraRequestDto data, @MappingTarget MediaExtra mediaExtra);

    public abstract Order patchOrder(OrderPatchRequestDTO dto, @MappingTarget Order order);

    public abstract Process mapObjectsToProcess(
            ProcessRequestDto data, @MappingTarget Process process);


}
