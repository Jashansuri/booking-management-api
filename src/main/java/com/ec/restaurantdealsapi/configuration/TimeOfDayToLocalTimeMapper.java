package com.ec.restaurantdealsapi.configuration;

import com.ec.restaurantdealsapi.Utils.TimeUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class TimeOfDayToLocalTimeMapper implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(final String source) {
        return TimeUtils.parseOrThrow(source);
    }
}
