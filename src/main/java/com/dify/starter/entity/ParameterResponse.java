package com.dify.starter.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterResponse {
    private String introduction;
    private List<Variable> variables;

    // Getters, setters, etc.

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Variable {
        private String key;
        private String name;
        private String description;
        private String type;
        @JsonProperty("default")
        private String default_value;
        private List<String> options;
    }
}

