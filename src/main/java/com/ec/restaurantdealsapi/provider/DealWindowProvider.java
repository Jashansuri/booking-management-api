package com.ec.restaurantdealsapi.provider;

import com.ec.restaurantdealsapi.Utils.TimeUtils;
import com.ec.restaurantdealsapi.client.ChallengeDataClient;
import com.ec.restaurantdealsapi.models.source.DealWindow;
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
