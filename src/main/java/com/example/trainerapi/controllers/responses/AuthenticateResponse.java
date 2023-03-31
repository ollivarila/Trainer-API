package com.example.trainerapi.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticateResponse {

    private String token;

    public static AuthenticateResponse of(String token) {
        return new AuthenticateResponse(token);
    }
}
