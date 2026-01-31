package com.ec.bookingmanagementapi.mapper;

import com.ec.bookingmanagementapi.models.response.DealDTO;
import com.ec.bookingmanagementapi.models.source.Deal;
import com.ec.bookingmanagementapi.models.source.Restaurant;

public class DealDTOMapper {

    public static DealDTO from(Restaurant restaurant, Deal deal) {
        return DealDTO.builder()
                .restaurantObjectId(restaurant.objectId())
                .restaurantName(restaurant.name())
                .restaurantAddress1(restaurant.address1())
                .restaurantSuburb(restaurant.suburb())
                .restaurantOpen(restaurant.open())
                .restaurantClose(restaurant.close())
                .dealObjectId(deal.objectId())
                .discount(deal.discount())
                .dineIn(deal.dineIn())
                .lightning(deal.lightning())
                .qtyLeft(deal.qtyLeft())
                .build();
    }
}
