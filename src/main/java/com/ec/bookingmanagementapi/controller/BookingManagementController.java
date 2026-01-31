package com.ec.bookingmanagementapi.controller;


import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ec")
@RequiredArgsConstructor
public class BookingManagementController {

    @GetMapping(value = "/get-deals")
    public String getDeals(@RequestParam @NotBlank String timeOfDay) {
        return "test response";
    }

}
