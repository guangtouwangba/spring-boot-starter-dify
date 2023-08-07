package com.dify.springbootstarterdify.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DifyResponse implements Serializable {
    private String answer;
    private String conversation_id;
    private Long created_at;
    private String id;
}
