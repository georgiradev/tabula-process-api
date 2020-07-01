package com.internship.tabulaprocessing.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderPatchRequestDTO {

    private Integer customerId;
    private String note;
    private Integer processStageId;
    private List<Integer> orderItemIds;
}
