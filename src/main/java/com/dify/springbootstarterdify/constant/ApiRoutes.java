package com.dify.springbootstarterdify.constant;

/**
 * @author guangtouwangba
 * @date 2023/8/7
 **/

import lombok.Getter;

/**
 * ApiRoutes is a enum class that contains all the routes that are used in the Dify API.
 */
@Getter
public enum ApiRoutes {
    APPLICATION("GET", "/parameters"),
    FEEDBACK("POST", "/messages/%s/feedbacks"),
    CREATE_COMPLETION_MESSAGE("POST", "/completion-messages"),
    CREATE_CHAT_MESSAGE("POST", "/chat-messages"),
    GET_CONVERSATION_MESSAGES("GET", "/messages"),
    GET_CONVERSATIONS("GET", "/conversations"),
    RENAME_CONVERSATION("PATCH", "/conversations/%s"),
    DELETE_CONVERSATION("DELETE", "/conversations/%s");

    private final String method;
    private final String url;

    ApiRoutes(String method, String url) {
        this.method = method;
        this.url = url;
    }

    public String getUrl(Object... args) {
        return String.format(url, args);
    }
}
