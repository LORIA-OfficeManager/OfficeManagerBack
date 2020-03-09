package com.OfficeManager.app.dtos;

public class MessageDto {

    private String type;
    private String message;

    public MessageDto(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
