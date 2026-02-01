package com.ec.bookingmanagementapi.Utils;


import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;


@UtilityClass
public class TimeUtils {
    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("h:mma").toFormatter(Locale.ENGLISH);

    public static LocalTime parseOrThrow(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("timeOfDay is required");
        }

        try {
            return LocalTime.parse(input, FORMATTER);
        } catch (DateTimeParseException ignored) {
            throw new IllegalArgumentException("Invalid timeOfDay: '" + input + "'. Use formats like '10:30pm' or '3:00pm'.");
        }
    }

    public static LocalTime parseOrNull(String input) {
        if (input == null || input.isBlank()) return null;
        try {
            return parseOrThrow(input);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    /*Ensuring only the start time is inclusive and not end time as deals at closing time won't matter*/
    public static boolean isDealActive(LocalTime time, LocalTime start, LocalTime end) {
        final var windowIsInSameDay = !end.isBefore(start);

        if (windowIsInSameDay) {
            // Same day window (Example: 14:00 -> 20:00)
            final var timeIsAfterDealStart = !time.isBefore(start);
            return timeIsAfterDealStart && time.isBefore(end);
        }

        // Overnight window (Example: 20:00 -> 02:00)
        final var timeIsAfterDealStart = !time.isBefore(start);
        return timeIsAfterDealStart || time.isBefore(end);
    }

    public static String format(LocalTime time) {
        return FORMATTER.format(time).toLowerCase(Locale.ENGLISH);
    }
}
