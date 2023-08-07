package com.dify.springbootstarterdify.client;

/**
 * @author guangtouwangba
 * @date 2023/8/7
 **/
import com.dify.springbootstarterdify.constant.ApiRoutes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class DifyClient {
    @Value("${dify.apiKey}")
    private String apiKey;

    @Value("${dify.baseUrl}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public DifyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void updateApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ResponseEntity<String> sendRequest(ApiRoutes apiRoute, HttpMethod method, Object... urlArgs) {
        String url = baseUrl + apiRoute.getUrl(urlArgs);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        return restTemplate.exchange(url, method, entity, String.class);
    }
}
