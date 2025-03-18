package com.lucasnscr.creditscore.client;

import com.lucasnscr.creditscore.record.CreditScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Component
public class ExternalBureauClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalBureauClient.class);

    private final WebClient webClient;

    public ExternalBureauClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public CreditScore requestBureau(int userId) {
        try {
            log.info("Requesting bureau: {}", Instant.now());
            return webClient.post()
                    .bodyValue(userId)
                    .retrieve()
                    .bodyToMono(CreditScore.class)
                    .block();
        } catch (Exception e) {
            log.warn("Error fetching data from bureau", e);
            return null;
        }
    }
}