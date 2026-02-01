package com.ec.restaurantdealsapi.models.source;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChallengeData(List<Restaurant> restaurants) {
    public ChallengeData {
        restaurants = (restaurants == null) ? List.of() : List.copyOf(restaurants);
    }
}
