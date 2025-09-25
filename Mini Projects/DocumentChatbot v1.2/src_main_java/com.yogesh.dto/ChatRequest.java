package com.yogesh.dto;

public class ChatRequest {
    private String message;
    private String model;

    // Default constructor for JSON binding
    public ChatRequest() {}

    public ChatRequest(String message, String model) {
        this.message = message;
        this.model = model;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}
