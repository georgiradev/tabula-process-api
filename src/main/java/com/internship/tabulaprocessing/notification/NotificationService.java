package com.internship.tabulaprocessing.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  @Autowired private SimpMessagingTemplate messagingTemplate;

  public void sendNotification(@Payload NotificationModel notificationModel) {

    messagingTemplate.convertAndSend(
        "/notifications/customer/" + notificationModel.getCustomerId(), notificationModel);
  }
}
