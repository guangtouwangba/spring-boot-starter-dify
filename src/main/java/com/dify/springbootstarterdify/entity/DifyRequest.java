package com.dify.springbootstarterdify.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DifyRequest implements Serializable {
    private Map<String, Object> inputs;
    private String query;
    private String response_mode;
    private String conversation_id;
    private String user;
}
