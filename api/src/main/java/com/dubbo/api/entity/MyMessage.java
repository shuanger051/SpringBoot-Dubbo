package com.dubbo.api.entity;

public class MyMessage {

    private String message;
    private Integer userId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "message='" + message + '\'' +
                ", userId=" + userId +
                '}';
    }

}
