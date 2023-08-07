package com.dify.starter.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebClientConfig {

    private final String baseUrl;


    public WebClientConfig(@Value("${dify.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public WebClient createWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .filter(logResponse())
                .build();
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            clientResponse.bodyToMono(String.class).subscribe(body ->
                    log.info("Response body: {}", body)
            );
            return Mono.just(clientResponse);
        });
    }
}