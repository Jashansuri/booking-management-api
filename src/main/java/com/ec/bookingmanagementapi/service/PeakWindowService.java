package com.ec.bookingmanagementapi.service;

import com.ec.bookingmanagementapi.Utils.TimeUtils;
import com.ec.bookingmanagementapi.client.ChallengeDataClient;
import com.ec.bookingmanagementapi.models.response.PeakTimeResponse;
import com.ec.bookingmanagementapi.models.source.DealWindow;
import com.ec.bookingmanagementapi.models.source.HourRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeakWindowService {
    private static final int HOURS_PER_DAY = 24;

    private final ChallengeDataClient client;

    public PeakTimeResponse calculatePeakWindow() {
        return PeakTimeResponse.empty();

//        final var dealWindows = loadDealWindows();
//        get active Deals per hour
//        countActiveDealsPerHour(dealWindows)

//        determine hourRange containing the most deals/overlapping deals
//        findBestPeakWindowRange(activeDealsPerHour);
//        return PeakTimeResponse(hour1, hour2)
    }

    private int[] countActiveDealsPerHour(List<DealWindow> dealWindows) {
        final var counts = new int[HOURS_PER_DAY];

//        TODO: Map over each hour and filter each dealWindow where the deal is active within

        return counts;
    }

    private HourRange findBestPeakWindowRange(int[] countsPerHour) {
//      TODO: Compare all counts to determine when the overlap is

        return new HourRange(1, 2);
    }

    private List<DealWindow> loadDealWindows() {
        return client.getChallengeData()
                .restaurants()
                .stream()
                .flatMap(restaurant -> {
                    var restaurantOpen = TimeUtils.parseOrNull(restaurant.open());
                    var restaurantClose = TimeUtils.parseOrNull(restaurant.close());

                    return restaurant.deals().stream()
                            .map(deal -> {
                                final var dealStart = Optional.ofNullable(TimeUtils.parseOrNull(deal.start()))
                                        .orElse(restaurantOpen);
                                final var dealEnd = Optional.ofNullable(TimeUtils.parseOrNull(deal.end()))
                                        .orElse(restaurantClose);
// TODO: refactor if restaurant timings not needed as deal times defaults to it anyway
                                return new DealWindow(
                                        restaurantOpen,
                                        restaurantClose,
                                        dealStart,
                                        dealEnd
                                );
                            });
                }).toList();
    }

}
