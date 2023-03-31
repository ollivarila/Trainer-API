package com.example.trainerapi.controllers;

import com.example.trainerapi.controllers.services.AuthenticationService;
import com.example.trainerapi.requestbody.LoginRequest;
import com.example.trainerapi.requestbody.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest req) {
        return authenticationService.authenticate(req);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req){
        return authenticationService.register(req);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LoginRequest req){
        System.out.println("LOGOUT");
        System.out.println(req.getUsername());
    }

    @GetMapping ("/test")
    public String test(){
        System.out.println("TEST");
        return "test";
    }
}
