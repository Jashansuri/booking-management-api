package com.ec.bookingmanagementapi.client;

import com.ec.bookingmanagementapi.configuration.BookingConfigurationProperties;
import com.ec.bookingmanagementapi.models.source.ChallengeData;
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
