package com.example.trainerapi.controllers;

import com.example.trainerapi.controllers.services.AuthenticationService;
import com.example.trainerapi.requestbody.LoginRequest;
import com.example.trainerapi.requestbody.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles user authentication and registration
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticates a user
     * @param req LoginRequest object containing username and password
     * @return ResponseEntity containing the JWT token
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest req) {
        return authenticationService.authenticate(req);
    }

    /**
     * Registers a user
     * @param req RegisterRequest object containing username, password
     * @return ResponseEntity containing the JWT token
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req){
        return authenticationService.register(req);
    }

    /**
     * Refreshes a JWT token
     * @param authHeader Authorization header containing the JWT token
     * @return ResponseEntity containing the new JWT token or an error
     */

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String authHeader){
        return authenticationService.refresh(authHeader);
    }
}
