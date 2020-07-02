package com.internship.tabulaprocessing.notification;

import com.internship.tabulaprocessing.dto.ProcessStageResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationModel {

    private Integer customerId;
    private Integer orderId;
    private NotificationType type;
    private ProcessStageResponseDTO processStage;
    private String message;

    public enum NotificationType{
        ORDER_CREATED,
        ORDER_UPDATED;
    }
}
