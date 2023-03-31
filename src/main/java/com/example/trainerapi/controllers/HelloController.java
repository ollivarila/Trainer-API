package com.example.trainerapi.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final Dotenv dotenv = Dotenv.configure().load();
    @GetMapping("/hello")
    public String hello() {
        return dotenv.get("TEST");
    }
}
