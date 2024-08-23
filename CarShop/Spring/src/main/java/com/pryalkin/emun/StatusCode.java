package com.pryalkin.emun;

public enum StatusCode {

    OK(200),
    CREATE(201);

    private Integer code;

    StatusCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}





