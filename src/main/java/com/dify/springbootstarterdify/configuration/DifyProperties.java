package com.dify.springbootstarterdify.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dify")
public class DifyProperties {
    private String apiKey;

}