package com.example.mychat.Models;

public class MessageModel {
    String message, id, time;
    Long timestamp;

    public MessageModel(String message, String id, String time, Long timestamp) {
        this.message = message;
        this.id = id;
        this.timestamp = timestamp;
        this.time = time;
    }

    public MessageModel(String message, String id) {
        this.message = message;
        this.id = id;
    }

    public MessageModel() {
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
