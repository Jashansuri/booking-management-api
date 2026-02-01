package com.ec.bookingmanagementapi.models.source;

import com.ec.bookingmanagementapi.Utils.TimeUtils;

import java.time.LocalTime;

public record DealWindow(LocalTime dealStart, LocalTime dealEnd, Restaurant restaurant,
                         Deal deal) {
    public boolean activeAt(LocalTime time) {
        return TimeUtils.isDealActive(time, dealStart, dealEnd);
    }
}
