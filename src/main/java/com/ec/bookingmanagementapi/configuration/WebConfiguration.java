package com.ec.bookingmanagementapi.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    private final TimeOfDayToLocalTimeMapper timeOfDayToLocalTimeMapper;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(timeOfDayToLocalTimeMapper);
    }
}
