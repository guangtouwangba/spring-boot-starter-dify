package com.dify.springbootstarterdify.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private Boolean has_more;
    private List<Data> data;

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private String id;
        private String username;
        private String phone_number;
        private String avatar_url;
        private String display_name;
        private String conversation_id;
        private Long created_at;
    }
}
