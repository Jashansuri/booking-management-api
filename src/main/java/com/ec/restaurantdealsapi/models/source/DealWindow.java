package com.ec.restaurantdealsapi.models.source;

import com.ec.restaurantdealsapi.Utils.TimeUtils;

import java.time.LocalTime;

public record DealWindow(LocalTime dealStart, LocalTime dealEnd, Restaurant restaurant,
                         Deal deal) {
    public boolean activeAt(LocalTime time) {
        return TimeUtils.isDealActive(time, dealStart, dealEnd);
    }
}
