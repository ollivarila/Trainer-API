package com.example.trainerapi.controllers.responses;


public class ErrorResponse {
    private final String error;

    public static ErrorResponse of(String error) {
        return new ErrorResponse(error);
    }
    private ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
