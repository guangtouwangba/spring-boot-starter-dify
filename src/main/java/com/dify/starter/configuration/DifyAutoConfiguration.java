package com.dify.starter.configuration;


import com.dify.starter.client.DifyClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DifyProperties.class)
public class DifyAutoConfiguration {

    /**
     * DifyClient is a class that contains all the routes that are used in the Dify API.
     */
    private final DifyClient difyClient;

    public DifyAutoConfiguration(DifyClient difyClient) {
        this.difyClient = difyClient;
    }


}
