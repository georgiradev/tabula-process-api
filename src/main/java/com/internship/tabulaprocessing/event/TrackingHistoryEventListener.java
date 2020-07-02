package com.internship.tabulaprocessing.event;

import com.internship.tabulaprocessing.service.TrackingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TrackingHistoryEventListener {

    @Autowired
    private TrackingHistoryService trackingHistoryService;

    @EventListener(CreateTrackingHistoryEvent.class)
    public void createTrackingHistory(CreateTrackingHistoryEvent event){

        trackingHistoryService.create(event.getOrder());
    }
}
