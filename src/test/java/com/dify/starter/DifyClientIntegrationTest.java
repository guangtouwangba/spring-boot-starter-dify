package com.dify.starter;

import com.dify.starter.client.DifyClient;
import com.dify.starter.entity.DifyRequest;
import com.dify.starter.entity.DifyResponse;
import com.dify.starter.entity.ParameterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")  // 如果你有专门为测试环境定义的profile，可以使用这个
public class DifyClientIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DifyClient difyClient;

    @Test
    public void testSendMessageBlocking() {
        // Arrange: Set up the expected request and data
        DifyRequest messageRequest = new DifyRequest();
        messageRequest.setInputs(Map.of("test-key", "test-value"));
        messageRequest.setConversation_id("test-conversation-id");
        messageRequest.setQuery("test-query");
        messageRequest.setResponse_mode("blocking");

        // Act: Call the method
        DifyResponse response = difyClient.sendMessageBlocking(messageRequest);

        // Assert: Check if the response is as expected
        assertNotNull(response);
    }
}