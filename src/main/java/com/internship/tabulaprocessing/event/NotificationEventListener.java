package com.internship.tabulaprocessing.event;

import com.internship.tabulaprocessing.entity.Order;
import com.internship.tabulaprocessing.mapper.Mapper;
import com.internship.tabulaprocessing.notification.NotificationModel;
import com.internship.tabulaprocessing.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationEventListener {

  private NotificationService notificationService;
  private Mapper mapper;

  @Autowired
  public NotificationEventListener(NotificationService notificationService, Mapper mapper) {
    this.notificationService = notificationService;
    this.mapper = mapper;
  }

  @EventListener(OrderUpdatedEvent.class)
  public void sendOrderUpdatedNotification(OrderUpdatedEvent event) {

    Order order = event.getOrder();
    NotificationModel model = createNotificationModel(order);
    model.setProcessStage(mapper.convertToProcessStageDTO(order.getProcessStage()));
    model.setType(NotificationModel.NotificationType.ORDER_UPDATED);
    model.setMessage(
        String.format(
            "Order with id %s, has been updated, its current process stage is %s",
            model.getOrderId(), model.getProcessStage().getName()));

    notificationService.sendNotification(model);
  }

  @EventListener(OrderCreatedEvent.class)
  public void sendOrderCreatedNotification(OrderCreatedEvent event) {

    Order order = event.getOrder();
    NotificationModel model = createNotificationModel(order);
    model.setProcessStage(mapper.convertToProcessStageDTO(order.getProcessStage()));
    model.setType(NotificationModel.NotificationType.ORDER_CREATED);
    model.setMessage(String.format("Order with id %s, has been created", model.getOrderId()));

    notificationService.sendNotification(model);
  }

  private NotificationModel createNotificationModel(Order order) {

    NotificationModel model = new NotificationModel();
    model.setCustomerId(order.getCustomer().getId());
    model.setOrderId(order.getId());

    return model;
  }
}
