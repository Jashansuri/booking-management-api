package com.ec.restaurantdealsapi.controller;


import com.ec.restaurantdealsapi.models.response.DealsResponse;
import com.ec.restaurantdealsapi.models.response.PeakTimeResponse;
import com.ec.restaurantdealsapi.service.DealsService;
import com.ec.restaurantdealsapi.service.PeakWindowService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@Validated
@RestController
@RequestMapping("/ec")
@RequiredArgsConstructor
public class BookingManagementController {
    private final DealsService dealsService;
    private final PeakWindowService peakWindowService;

    @GetMapping(value = "/deals")
    public DealsResponse getDeals(@RequestParam @NotNull LocalTime timeOfDay) {
        return new DealsResponse(dealsService.findActiveDeals(timeOfDay));
    }

    @GetMapping("/peak-window")
    public PeakTimeResponse getPeakWindow() {
        return peakWindowService.calculatePeakWindow();
    }
}
