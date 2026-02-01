package com.ec.bookingmanagementapi.models.source;

import java.time.LocalTime;

public record DealWindow(LocalTime restaurantOpen, LocalTime restaurantClose, LocalTime dealStart, LocalTime dealEnd) {
}
