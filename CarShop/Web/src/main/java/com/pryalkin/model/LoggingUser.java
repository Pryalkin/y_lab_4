package com.pryalkin.model;

public class LoggingUser {

    private String id;
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

    public String getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
