package com.example.trainerapi.controllers;

import com.example.trainerapi.controllers.services.UserService;
import com.example.trainerapi.models.entities.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/workouts")
    public ResponseEntity<?> getWorkouts(@RequestHeader("Authorization") String auth) {
        return userService.getWorkouts(auth);
    }
    @PostMapping("/workouts")
    public ResponseEntity<?> addWorkout(@RequestHeader("Authorization") String auth,  @RequestBody Workout workout) {
        return userService.addWorkout(auth, workout);
    }
    @DeleteMapping("/workouts/{workoutId}")
    public ResponseEntity<?> deleteWorkout(@RequestHeader("Authorization") String auth, @PathVariable String workoutId) {
        return userService.deleteWorkout(auth, workoutId);
    }
    @GetMapping("/exercisetypes")
    public ResponseEntity<?> getExerciseTypes(@RequestHeader("Authorization") String auth) {
        return userService.getExerciseTypes(auth);
    }
}
