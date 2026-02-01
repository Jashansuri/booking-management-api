package com.ec.restaurantdealsapi.client;

import com.ec.restaurantdealsapi.configuration.BookingConfigurationProperties;
import com.ec.restaurantdealsapi.models.source.ChallengeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ChallengeDataClient {
    private final RestClient restClient;
    private final BookingConfigurationProperties props;

    public ChallengeData getChallengeData() {
        return restClient.get()
                .uri(props.url())
                .retrieve()
                .body(ChallengeData.class);
    }
}
