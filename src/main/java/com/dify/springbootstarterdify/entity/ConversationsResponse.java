package com.dify.springbootstarterdify.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationsResponse {
    private int limit;
    private Boolean has_more;
    private List<ConversationData> data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConversationData {
        private String id;
        private String name;
        private Map<String, Object> inputs;
        private String status;
        private Long created_at;
    }
}
