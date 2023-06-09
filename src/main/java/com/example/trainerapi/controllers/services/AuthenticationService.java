package com.example.trainerapi.controllers.services;

import com.example.trainerapi.controllers.responses.AuthenticateResponse;
import com.example.trainerapi.controllers.responses.ErrorResponse;
import com.example.trainerapi.controllers.responses.RegisterResponse;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.requestbody.LoginRequest;
import com.example.trainerapi.requestbody.RegisterRequest;
import com.example.trainerapi.security.util.JwtTokenUtil;
import com.example.trainerapi.security.util.UserCreationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final UserCreationUtil userCreationUtil;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserCreationUtil userCreationUtil) {
        this.userCreationUtil = userCreationUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    public ResponseEntity<?> authenticate(LoginRequest req){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        User user = userRepository.findByUsername(req.getUsername());
        String token = JwtTokenUtil.generate(user.getUsername());
        return ResponseEntity.ok(AuthenticateResponse.of(token));
    }
    public ResponseEntity<?> register(RegisterRequest req) {
        if(userRepository.existsTrainerUserByUsername(req.getUsername())){
            return ResponseEntity.badRequest().body(ErrorResponse.of("Username already exists"));
        }
        String encodedPassword = passwordEncoder.encode(req.getPassword());
        User newUser = new User(req.getUsername(), encodedPassword);
        User saved = userRepository.save(newUser);
        userCreationUtil.createDefaultExerciseTypes(saved);
        return ResponseEntity.ok(RegisterResponse.from(JwtTokenUtil.generate(req.getUsername()), saved.getUsername()));
    }

    public ResponseEntity<?> refresh(String authHeader) {
        String token = authHeader.substring(7);
        String username = JwtTokenUtil.getUsername(token);
        if(username == null){
            return ResponseEntity.badRequest().body(ErrorResponse.of("Invalid token"));
        }
        return ResponseEntity.ok(AuthenticateResponse.of(JwtTokenUtil.generate(username)));
    }
}
