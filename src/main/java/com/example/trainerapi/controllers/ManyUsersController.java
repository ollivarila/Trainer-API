package com.example.trainerapi.controllers;

import com.example.trainerapi.controllers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle requests coming to /api/users endpoint
 */

@RestController
@RequestMapping("/api/users")
public class ManyUsersController {

    private final UserService userService;

    @Autowired
    public ManyUsersController(UserService userService) {
        this.userService = userService;
    }

    /**
     * gets all usernames from database
     * doesnt require authentication
     * @return responseentity containing usernames in a list as a json string
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }
}
