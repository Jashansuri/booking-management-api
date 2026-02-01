package com.ec.restaurantdealsapi.models.source;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Deal(String objectId,
                   String discount,
                   String dineIn,
                   String lightning,
                   String qtyLeft,
                   @JsonAlias({"open", "start"})
                   String start,
                   @JsonAlias({"close", "end"})
                   String end) {
}
