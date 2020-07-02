package com.internship.tabulaprocessing.event;

import com.internship.tabulaprocessing.entity.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class CreateTrackingHistoryEvent extends ApplicationEvent {

    private Order order;

    public CreateTrackingHistoryEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }
}
