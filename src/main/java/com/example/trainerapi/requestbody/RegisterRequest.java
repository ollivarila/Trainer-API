package com.example.trainerapi.requestbody;

public class RegisterRequest extends LoginRequest{
    public RegisterRequest(String username, String password) {
        super(username, password);
    }
}
