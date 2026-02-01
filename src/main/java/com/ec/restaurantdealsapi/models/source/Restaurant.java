package com.ec.restaurantdealsapi.models.source;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Restaurant(String objectId,
                         String name,
                         String address1,
                         String suburb,
                         String open,
                         String close,
                         String imageLink,
                         List<String> cuisines,
                         List<Deal> deals) {
    public Restaurant {
        deals = (deals == null) ? List.of() : List.copyOf(deals);
    }
}

