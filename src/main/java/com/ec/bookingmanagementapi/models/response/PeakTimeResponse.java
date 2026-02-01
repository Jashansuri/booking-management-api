package com.ec.bookingmanagementapi.models.response;

public record PeakTimeResponse(String peakTimeStart, String peakTimeEnd) {
    public static PeakTimeResponse empty() {
        return new PeakTimeResponse(null, null);
    }
}
