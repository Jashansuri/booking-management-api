package com.ec.restaurantdealsapi.client;

import com.ec.restaurantdealsapi.configuration.BookingConfigurationProperties;
import com.ec.restaurantdealsapi.models.source.ChallengeData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChallengeDataClient {
    private final RestClient restClient;
    private final BookingConfigurationProperties props;

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public ChallengeData getChallengeData() {
        try {
            return fetchFromRemote();
        } catch (Exception ex) {
            log.warn("Failed to fetch challenge data from remote, falling back to local file", ex);
            return loadFromClasspath();
        }
    }

    private ChallengeData fetchFromRemote() {
        return restClient.get()
                .uri(props.url())
                .retrieve()
                .body(ChallengeData.class);
    }

    private ChallengeData loadFromClasspath() {
        try (InputStream is = resourceLoader.getResource("classpath:" + props.fallbackResource()).getInputStream()) {
            return objectMapper.readValue(is, ChallengeData.class);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load fallback challenge data from classpath", ex);
        }
    }

}
