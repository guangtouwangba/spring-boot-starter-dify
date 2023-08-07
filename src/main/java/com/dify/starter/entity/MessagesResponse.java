package com.dify.starter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagesResponse {
    private Boolean has_more;
    private List<MessageData> data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageData {
        private String id;
        private String conversation_id;
        private Map<String, Object> inputs;
        private String query;
        private String answer;
        private String feedback;
        private Long created_at;
    }
}
