package com.ec.bookingmanagementapi.controller;


import com.ec.bookingmanagementapi.models.response.DealsResponse;
import com.ec.bookingmanagementapi.service.DealsService;
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

    @GetMapping(value = "/get-deals")
    public DealsResponse getDeals(@RequestParam @NotNull LocalTime timeOfDay) {
        return new DealsResponse(dealsService.findActiveDeals(timeOfDay));
    }
}
