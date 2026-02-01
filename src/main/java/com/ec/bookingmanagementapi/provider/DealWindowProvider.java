package com.ec.bookingmanagementapi.provider;

import com.ec.bookingmanagementapi.Utils.TimeUtils;
import com.ec.bookingmanagementapi.client.ChallengeDataClient;
import com.ec.bookingmanagementapi.models.source.DealWindow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DealWindowProvider {

    private final ChallengeDataClient client;

    public List<DealWindow> loadDealWindows() {
        return client.getChallengeData()
                .restaurants()
                .stream()
                .flatMap(restaurant -> restaurant.deals().stream()
                        .map(deal -> {
                            final var dealStart = Optional.ofNullable(TimeUtils.parseOrNull(deal.start()))
                                    .orElse(TimeUtils.parseOrNull(restaurant.open()));
                            final var dealEnd = Optional.ofNullable(TimeUtils.parseOrNull(deal.end()))
                                    .orElse(TimeUtils.parseOrNull(restaurant.close()));

                            return new DealWindow(dealStart, dealEnd, restaurant, deal);
                        })
                ).toList();
    }
}
