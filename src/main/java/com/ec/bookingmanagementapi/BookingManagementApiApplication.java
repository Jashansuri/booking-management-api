package com.ec.bookingmanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BookingManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingManagementApiApplication.class, args);
	}

}
