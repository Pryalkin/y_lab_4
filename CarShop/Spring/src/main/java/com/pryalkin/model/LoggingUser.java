package com.pryalkin.model;

import lombok.Data;

@Data
public class LoggingUser {

    private Long id;
    private Integer userId;
    private String action;
    private String date;

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", userId:'" + userId + '\'' +
                ", action:'" + action + '\'' +
                ", date:'" + date + '\'' +
                '}';
    }

}
