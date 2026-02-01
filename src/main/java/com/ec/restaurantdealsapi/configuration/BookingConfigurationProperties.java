package com.ec.restaurantdealsapi.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "restaurant-deals")
public record BookingConfigurationProperties(@NotNull URI url, @NotBlank String fallbackResource) {
}
