package com.internship.tabulaprocessing.event;

import com.internship.tabulaprocessing.entity.Order;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderUpdatedEvent extends ApplicationEvent {

    private Order order;

    public OrderUpdatedEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }
}
