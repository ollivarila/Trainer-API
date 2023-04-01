package com.example.trainerapi.controllers;

import com.example.trainerapi.controllers.services.AuthenticationService;
import com.example.trainerapi.requestbody.LoginRequest;
import com.example.trainerapi.requestbody.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles user authentication and registration
 */
@RestController
// todo ei tarvi tässä, lombokia tarvii vain Entityissä
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    // todo @Autowired
    private final AuthenticationService authenticationService;

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
