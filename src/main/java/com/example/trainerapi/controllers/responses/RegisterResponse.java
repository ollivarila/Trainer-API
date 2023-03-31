package com.example.trainerapi.controllers.responses;

public class RegisterResponse {
    private String token;
    private String username;

    public static RegisterResponse from(String token, String username) {
        return new RegisterResponse(token, username);
    }

    private RegisterResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}
