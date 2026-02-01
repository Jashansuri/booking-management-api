package com.ec.bookingmanagementapi.models.source;

public record HourRange(int startingHour, int endingHour) {
    public int length() {
        return endingHour - startingHour + 1;
    }
}