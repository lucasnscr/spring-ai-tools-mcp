package com.lucasnscr.creditscore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Configuration
public class WebClientConfig {

    private static final String HEADER_API_KEY = "x-api-key";
    private static final String CONTENT_TYPE = "application/json";

    @Value("${mock.bureau.api.host}")
    private String MOCK_BUREAU_API_HOST;
    @Value("${mock.bureau.api.key}")
    private String MOCK_BUREAU_APIKEY;

    @Bean
    public WebClient bureauClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(MOCK_BUREAU_API_HOST)
                .defaultHeader(HEADER_API_KEY, MOCK_BUREAU_APIKEY)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
                        .build())
                .build();
    }
}
