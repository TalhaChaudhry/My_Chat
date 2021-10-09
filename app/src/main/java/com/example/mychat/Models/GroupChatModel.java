package com.example.mychat.Models;

public class GroupChatModel {
    String message, id, time, senderName;
    Long timeStamp;

    public GroupChatModel(String message, String id, String time, String senderName, Long timeStamp) {
        this.message = message;
        this.id = id;
        this.time = time;
        this.senderName = senderName;
        this.timeStamp = timeStamp;
    }

    public GroupChatModel(String message, String id) {
        this.message = message;
        this.id = id;
    }

    public GroupChatModel() {
    }

    public String getMessage() {
        return message;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
