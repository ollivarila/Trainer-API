package com.example.trainerapi.controllers.responses;

import lombok.Getter;

@Getter
public class AuthenticateResponse {

    private final String token;

    private AuthenticateResponse(String token) {
        this.token = token;
    }

    public static AuthenticateResponse of(String token) {
        return new AuthenticateResponse(token);
    }
}
