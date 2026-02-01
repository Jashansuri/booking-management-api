package com.ec.bookingmanagementapi.service;

import com.ec.bookingmanagementapi.Utils.TimeUtils;
import com.ec.bookingmanagementapi.client.ChallengeDataClient;
import com.ec.bookingmanagementapi.models.response.PeakTimeResponse;
import com.ec.bookingmanagementapi.models.source.DealWindow;
import com.ec.bookingmanagementapi.models.source.HourRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeakWindowService {
    private static final int HOURS_PER_DAY = 24;

    private final ChallengeDataClient client;

    public PeakTimeResponse calculatePeakWindow() {
        final var dealWindows = loadDealWindows();
        if (dealWindows.isEmpty()) {
            return PeakTimeResponse.empty();
        }

        final var activeDealsPerHour = countActiveDealsPerHour(dealWindows);

        final var maxActiveDeals = Arrays.stream(activeDealsPerHour).max().orElse(0);
        if (maxActiveDeals == 0) {
            return PeakTimeResponse.empty();
        }

        final var peakRange = findPeakWindowRange(activeDealsPerHour, maxActiveDeals);

        final var peakStart = LocalTime.of(peakRange.startHour(), 0);
        final var peakEnd = LocalTime.of(peakRange.endHour() + 1, 0);

        return new PeakTimeResponse(TimeUtils.format(peakStart), TimeUtils.format(peakEnd));
    }

    private int[] countActiveDealsPerHour(List<DealWindow> dealWindows) {
        int[] counts = new int[HOURS_PER_DAY];

        for (DealWindow window : dealWindows) {
            for (int hour = 0; hour < HOURS_PER_DAY; hour++) {
                final var sampleTime = LocalTime.of(hour, 30);
                if (TimeUtils.isDealActive(sampleTime, window.dealStart(), window.dealEnd())) {
                    counts[hour]++;
                }
            }
        }

        return counts;
    }

    private HourRange findPeakWindowRange(int[] counts, int peakValue) {
//        TODO: Determine Peak Window by calculating longest consecutive range exactly max value
//         -> alternatively need to look at either sliding window or give threshold to allow for small dips
//         e.g. 3-5pm 10 deals, 5-6pm 7 deals then 6-9pm again 10 deals. that whole time can then potentially be a window?

        return new HourRange(1, 2);
    }

    private List<DealWindow> loadDealWindows() {
        return client.getChallengeData()
                .restaurants()
                .stream()
                .flatMap(restaurant -> restaurant.deals().stream()
                        .map(deal -> {
                            final var dealStart = Optional.ofNullable(TimeUtils.parseOrNull(deal.start()))
                                    .orElse(TimeUtils.parseOrNull(restaurant.open()));
                            final var dealEnd = Optional.ofNullable(TimeUtils.parseOrNull(deal.end()))
                                    .orElse(TimeUtils.parseOrNull(restaurant.close()));

                            return new DealWindow(dealStart, dealEnd);
                        })
                ).toList();
    }

}
