package com.pryalkin.controller;

import com.pryalkin.emun.StatusCode;
import org.apache.http.Header;

public class ResponseEntity<T> {

    private T data;
    private StatusCode statusCode;
    private Header header;

    public ResponseEntity(T data, StatusCode statusCode) {
        this.data = data;
        this.statusCode = statusCode;
    }

    public ResponseEntity(T data, StatusCode statusCode, Header header) {
        this.data = data;
        this.statusCode = statusCode;
        this.header = header;
    }

    public T getData() {
        return data;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public Header getHeader() {
        return header;
    }
}
