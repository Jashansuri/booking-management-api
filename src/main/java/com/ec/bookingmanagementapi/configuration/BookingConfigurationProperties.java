package com.ec.bookingmanagementapi.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "restaurant-deals")
public record BookingConfigurationProperties(@NotNull URI sourceUrl) {}
