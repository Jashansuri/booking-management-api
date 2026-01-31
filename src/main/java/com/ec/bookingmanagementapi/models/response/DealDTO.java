package com.ec.bookingmanagementapi.models.response;

import lombok.Builder;

@Builder
public record DealDTO(String restaurantObjectId, String restaurantName, String restaurantAddress1,
                      String restaurantSuburb, String restaurantOpen, String restaurantClose, String dealObjectId,
                      String discount, String dineIn, String lightning, String qtyLeft) {
}
