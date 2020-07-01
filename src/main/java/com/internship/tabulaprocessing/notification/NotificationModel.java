package com.internship.tabulaprocessing.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationModel {

    private Integer customerId;
    private Integer orderId;
    private NotificationType type;
    private int currentProcessStageId;
    private String message;

    public enum NotificationType{
        ORDER_CREATED,
        ORDER_UPDATED;
    }
}
