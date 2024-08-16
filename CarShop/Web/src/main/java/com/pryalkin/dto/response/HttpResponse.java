package com.pryalkin.dto.response;

import org.apache.http.HttpStatus;

public class HttpResponse {
    private int httpStatusCode;
    private String reason;
    private String message;

    public HttpResponse(int httpStatusCode, String reason, String message) {
        this.httpStatusCode = httpStatusCode;
        this.reason = reason;
        this.message = message;
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
}