package com.ec.bookingmanagementapi.models.source;

import java.time.LocalTime;

public record DealWindow(LocalTime dealStart, LocalTime dealEnd) {
}
