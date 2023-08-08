package com.dify.starter;

import com.dify.starter.client.DifyClient;
import com.dify.starter.entity.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DifyClientIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DifyClient difyClient;

    @Nested
    class Test_send_message {
        @Test
        public void test_BadRequest_for_send_message() {
            // Arrange: Set up the expected request and data
            DifyRequest messageRequest = new DifyRequest();
            messageRequest.setInputs(Map.of("test-key", "test-value"));
            messageRequest.setConversation_id("test-conversation-id");
            messageRequest.setQuery("test-query");
            messageRequest.setResponse_mode("blocking");

            // Act: Call the method
            Object response = difyClient.sendMessageBlocking(messageRequest);

            // Assert: Check if the response is as expected
            assertNotNull(response);
            assertEquals(FailedResponse.class, response.getClass());
        }

        @Test
        void should_return_correct_response() {
            // Arrange: Set up the expected request and data
            DifyRequest messageRequest = new DifyRequest();
            messageRequest.setInputs(Map.of("test-key", "test-value"));
            messageRequest.setConversation_id("");
            messageRequest.setQuery("test-query");
            messageRequest.setResponse_mode("blocking");
            messageRequest.setUser("test-user");

            // Act: Call the method
            Object response = difyClient.sendMessageBlocking(messageRequest);

            // Assert: Check if the response is as expected
            assertNotNull(response);
            assertEquals(DifyResponse.class, response.getClass());
        }
    }

    @Nested
    class Test_sendMessageStreaming {
        @Test
        void should_return_streaming_value() {
            DifyRequest messageRequest = new DifyRequest();
            messageRequest.setInputs(Map.of("test-key", "帮我一个三体的描述"));
            messageRequest.setConversation_id("");
            messageRequest.setQuery("test-query");
            messageRequest.setResponse_mode("streaming");
            messageRequest.setUser("test-user");

            Flux<DifyResponse> responseFlux = difyClient.sendMessageStreaming(messageRequest);

            StepVerifier.create(responseFlux)
                    .thenAwait(Duration.ofSeconds(10))  // This will wait for 10 seconds
                    .expectNextCount(10)  // Expect 10 items in that duration (change as per your needs)
                    .thenCancel()  // Then cancel the Flux after consuming those items
                    .verify();
        }
    }

    @Nested
    class Test_send_feedback_message {
        @Test
        void should_send_feedback_message_successfully() {
            // create a conversation
            DifyRequest messageRequest = DifyRequest.builder()
                    .conversation_id("")
                    .inputs(Map.of("test-key", "帮我一个三体的描述"))
                    .query("test-query")
                    .response_mode("blocking")
                    .user("test-user")
                    .build();
            Object response = difyClient.sendMessageBlocking(messageRequest);
            String messageID = response.getClass().equals(DifyResponse.class) ?
                    ((DifyResponse) response).getId() : null;
            System.out.println(messageID);
            assertNotNull(messageID);

            // send feedback message
            FeedbackRequest feedbackRequest = FeedbackRequest.builder()
                    .rating("like")
                    .user("test-user")
                    .build();

            FeedbackResponse feedbackResponse = difyClient.sendFeedback(messageID, feedbackRequest);

            assertNotNull(feedbackResponse);
            assertNotNull(feedbackResponse.getResult());
        }
    }
}