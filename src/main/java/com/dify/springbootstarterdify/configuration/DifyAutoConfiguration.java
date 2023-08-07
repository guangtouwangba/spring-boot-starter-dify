package com.dify.springbootstarterdify.configuration;


import com.dify.springbootstarterdify.serivce.DifyService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DifyProperties.class)
public class DifyAutoConfiguration {

    /**
     * DifyService bean
     */
    private final DifyService difyService;

    public DifyAutoConfiguration(DifyService difyService) {
        this.difyService = difyService;
    }

    @Bean
    public DifyService difyService() {
        return difyService;
    }

}
