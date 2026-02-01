package com.ec.restaurantdealsapi.service;

import com.ec.restaurantdealsapi.Utils.TimeUtils;
import com.ec.restaurantdealsapi.models.response.PeakTimeResponse;
import com.ec.restaurantdealsapi.models.source.DealWindow;
import com.ec.restaurantdealsapi.models.source.HourRange;
import com.ec.restaurantdealsapi.provider.DealWindowProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeakWindowService {
    private static final int HOURS_PER_DAY = 24;

    private final DealWindowProvider windowProvider;

    public PeakTimeResponse calculatePeakWindow() {
        final var dealWindows = windowProvider.loadDealWindows();
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

    private int[] countActiveDealsPerHour(List<DealWindow> dealWindows) {
        int[] counts = new int[HOURS_PER_DAY];

        for (DealWindow window : dealWindows) {
            for (int hour = 0; hour < HOURS_PER_DAY; hour++) {
                final var sampleTime = LocalTime.of(hour, 30);
                if (window.activeAt(sampleTime)) {
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
