package com.ec.bookingmanagementapi.service;

import com.ec.bookingmanagementapi.mapper.DealDTOMapper;
import com.ec.bookingmanagementapi.models.response.DealDTO;
import com.ec.bookingmanagementapi.provider.DealWindowProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealsService {
    private final DealWindowProvider windowProvider;

    /**
     * Assumed that if start/end time is missing from deals but deal is present then deal would be active during restaurant open and close hours
     **/
    public List<DealDTO> findActiveDeals(final LocalTime timeOfDay) {
        return windowProvider.loadDealWindows().stream()
                .filter(dealWindow -> dealWindow.activeAt(timeOfDay))
                .map(dealWindow -> DealDTOMapper.from(dealWindow.restaurant(), dealWindow.deal()))
                .toList();
    }
}
