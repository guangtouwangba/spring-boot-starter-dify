package com.dify.starter.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    private final String baseUrl;


    public WebClientConfig(@Value("${dify.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Bean
    public WebClient create() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}