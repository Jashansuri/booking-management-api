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

        final var peakStart = LocalTime.of(peakRange.startingHour(), 0);
        final var peakEnd = LocalTime.of(peakRange.endingHour() + 1, 0);

        return new PeakTimeResponse(TimeUtils.format(peakStart), TimeUtils.format(peakEnd));
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

    /**
     * Finding the longest consecutive range of hours that have the peak value/counts of deals.
     * E.g. if hours 2-5 and 8-12 both have peak value, this returns 8-12 (longer range).
     *
     * @param countOfActiveDeals Array where each index represents an hour and value is number of active deals
     * @param peakDealsCount     The maximum number of active deals we're looking for
     * @return HourRange or null if no peak found
     */
    private HourRange findPeakWindowRange(int[] countOfActiveDeals, int peakDealsCount) {
        HourRange longestRange = null;
        int maxLength = 0;

        for (int hour = 0; hour < countOfActiveDeals.length; hour++) {
            // Skip every hour that doesn't have the peakDealsCount
            if (countOfActiveDeals[hour] != peakDealsCount) continue;

            int windowEnd = hour;
            // increment ending hour if subsequent hour is also peakDealsCount
            while (windowEnd + 1 < countOfActiveDeals.length && countOfActiveDeals[windowEnd + 1] == peakDealsCount) {
                windowEnd++;
            }
            final HourRange currentRange = new HourRange(hour, windowEnd);

            // Update if range is longer than current peakDealsCount
            if (currentRange.length() > maxLength) {
                maxLength = currentRange.length();
                longestRange = currentRange;
            }

            // set hour to be ending hour to avoid reprocessing already processed hours
            hour = currentRange.endingHour();
        }

        return longestRange;
    }
}
