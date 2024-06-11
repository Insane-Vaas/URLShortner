package com.example.URLShortner.Domain;

public class ErrorResponse {

    private String message;


    public ErrorResponse(String message) {

        this.message = message;

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "ErrorResponse{" +
                ", message='" + message + '\'' +
                '}';
    }
}
