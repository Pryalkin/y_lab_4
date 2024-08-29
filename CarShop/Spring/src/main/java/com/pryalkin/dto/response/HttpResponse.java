package com.pryalkin.dto.response;

import java.util.Map;

public class HttpResponse<V> {
    private int httpStatusCode;
    private String reason;
    private String message;
    private Map<String, V> body;

    public HttpResponse(int httpStatusCode, String reason, String message, Map<String, V> body) {
        this.httpStatusCode = httpStatusCode;
        this.reason = reason;
        this.message = message;
        this.body = body;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, V> getBody() {
        return body;
    }
}