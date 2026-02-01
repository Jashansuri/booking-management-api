package com.ec.restaurantdealsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RestaurantDealsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantDealsApplication.class, args);
	}

}
