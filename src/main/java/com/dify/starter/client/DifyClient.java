package com.dify.starter.client;

/**
 * @author guangtouwangba
 * @date 2023/8/7
 **/

import com.dify.starter.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DifyClient {
    @Value("${dify.apiKey}")
    private String apiKey;

    @Value("${dify.baseUrl}")
    private String baseUrl;


    private final WebClient webClient;

    public DifyClient() {
        this.webClient = WebClient.create();
    }

    public DifyClient(String apiKey, String baseUrl) {
        this.webClient = WebClient.create();
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public DifyClient(String apiKey) {
        this.webClient = WebClient.create();
        this.apiKey = apiKey;
    }

    public DifyResponse sendMessageBlocking(DifyRequest request) {
        return webClient.post()
                .uri(baseUrl)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DifyResponse.class)
                .block();
    }

    public Flux<DifyResponse> sendMessageStreaming(DifyRequest request) {
        return webClient.post()
                .uri(baseUrl)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(DifyResponse.class);
    }

    public FeedbackResponse sendFeedback(String messageId, FeedbackRequest request) {
        return webClient.post()
                .uri(baseUrl + "/messages/" + messageId + "/feedbacks")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FeedbackResponse.class)
                .block();
    }


    public MessagesResponse getMessages(String user, String conversationId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrl + "/messages")
                        .queryParam("user", user)
                        .queryParam("conversation_id", conversationId)
                        .build())
                .header("Authorization", "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(MessagesResponse.class)
                .block();
    }

    public CreateConversationResponse createConversationWithName(String name, String user) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("user", user);

        return webClient.post()
                .uri(baseUrl + "/conversations/name")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(CreateConversationResponse.class)
                .block();
    }

    public CreateConversationResponse deleteConversation(String conversationId, String user) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(baseUrl + "/conversations/{conversationId}")
                        .queryParam("user", user)
                        .build(conversationId))
                .header("Authorization", "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(CreateConversationResponse.class)
                .block();
    }

    public AudioToTextResponse audioToText(MultipartFile audioFile) throws IOException {
        String contentType = audioFile.getContentType();
        if (contentType == null || !contentType.startsWith("audio/")) {
            throw new IllegalArgumentException("Unsupported file type");
        }

        ByteArrayResource byteArrayResource = new ByteArrayResource(audioFile.getBytes()) {
            @Override
            public String getFilename() {
                return audioFile.getOriginalFilename();
            }
        };

        return webClient.post()
                .uri(baseUrl + "/audio-to-text")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromFormData("file", String.valueOf(byteArrayResource)))
                .retrieve()
                .bodyToMono(AudioToTextResponse.class)
                .block();
    }

    public ParameterResponse fetchParameters() {
        return webClient.get()
                .uri(baseUrl +"/parameters")
                .header("Authorization", "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(ParameterResponse.class)
                .block();
    }
}
