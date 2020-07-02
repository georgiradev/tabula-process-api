package com.internship.tabulaprocessing.provider;

import com.internship.tabulaprocessing.entity.TrackingHistory;

import java.time.LocalDateTime;

public class TrackingHistoryProvider {

    public static TrackingHistory getTrackingHistory(){

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(1);
        trackingHistory.setDateTimeUpdated(LocalDateTime.now());
        trackingHistory.setAssigneeId(1);
        trackingHistory.setProcessStageId(1);
        trackingHistory.setOrderId(1);

        return trackingHistory;
    }
}
