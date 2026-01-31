package com.ec.bookingmanagementapi.service;

import com.ec.bookingmanagementapi.Utils.TimeUtils;
import com.ec.bookingmanagementapi.client.ChallengeDataClient;
import com.ec.bookingmanagementapi.mapper.DealDTOMapper;
import com.ec.bookingmanagementapi.models.response.DealDTO;
import com.ec.bookingmanagementapi.models.source.Deal;
import com.ec.bookingmanagementapi.models.source.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealsService {

    private final ChallengeDataClient client;

    /**
     * Assumed that if start/end time is missing from deals but deal is present then deal would be active during restaurant open and close hours
     **/
    public List<DealDTO> findActiveDeals(final LocalTime timeOfDay) {
        return client.getChallengeData().restaurants().stream()
                .flatMap(restaurant -> getActiveDealsForRestaurant(restaurant, timeOfDay))
                .toList();
    }

    private Stream<DealDTO> getActiveDealsForRestaurant(final Restaurant restaurant, final LocalTime timeOfDay) {
        var restaurantOpen = TimeUtils.parseOrNull(restaurant.open());
        var restaurantClose = TimeUtils.parseOrNull(restaurant.close());

        return restaurant.deals().stream().filter(deal -> isDealOrRestaurantActiveAt(
                        deal,
                        restaurantOpen,
                        restaurantClose,
                        timeOfDay
                ))
                .map(deal -> DealDTOMapper.from(restaurant, deal));
    }

    private boolean isDealOrRestaurantActiveAt(final Deal deal, final LocalTime restaurantOpen, final LocalTime restaurantClose, final LocalTime timeOfDay) {
        var startTime = parseDealTimeOrFallbackToRestaurantTime(
                deal.start(),
                restaurantOpen
        );
        var endTime = parseDealTimeOrFallbackToRestaurantTime(
                deal.end(),
                restaurantClose
        );

        return TimeUtils.isDealActive(timeOfDay, startTime, endTime);
    }

    private LocalTime parseDealTimeOrFallbackToRestaurantTime(final String dealTime, final LocalTime fallbackRestaurantTime) {
        return Optional.ofNullable(TimeUtils.parseOrNull(dealTime))
                .orElse(fallbackRestaurantTime);
    }
}
