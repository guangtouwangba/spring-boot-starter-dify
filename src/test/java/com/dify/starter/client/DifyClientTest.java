package com.dify.starter.client;

import com.dify.starter.entity.ParameterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DifyClientTest {

    @Autowired
    private DifyClient difyClient;

    @MockBean
    private WebClient webClient;

    @MockBean
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @MockBean
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @MockBean
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    public void setup() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/parameters")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header("Authorization", "Bearer " + "test-secret-key")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    public void testFetchParameters() {
        ParameterResponse mockResponse = new ParameterResponse();
        // Populate the mockResponse with dummy data if needed.

        when(responseSpec.bodyToMono(ParameterResponse.class)).thenReturn(Mono.just(mockResponse));

        ParameterResponse result = difyClient.fetchParameters();

        // Assert the result (you may need to add other assertions based on your exact requirements)
        assertNotNull(result);
        // More assertions here...
    }

    @Test
    public void testFetchParametersError() {
        when(responseSpec.bodyToMono(ParameterResponse.class))
                .thenReturn(Mono.error(new WebClientResponseException("Not Found", 404, "Not Found",
                        null, null, null)));

        // Here, we're testing if our function properly handles errors. You can expand on this if you have specific error-handling logic in your methods.
        assertThrows(WebClientResponseException.class, () -> difyClient.fetchParameters());
    }
}
