package com.ec.restaurantdealsapi.models.source;

public record HourRange(int startingHour, int endingHour) {
    public int length() {
        return endingHour - startingHour + 1;
    }
}