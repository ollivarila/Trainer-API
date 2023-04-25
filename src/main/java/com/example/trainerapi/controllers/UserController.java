package com.example.trainerapi.controllers;

import com.example.trainerapi.controllers.services.UserService;
import com.example.trainerapi.models.entities.ExerciseType;
import com.example.trainerapi.models.entities.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Controller for user related endpoints.
 * Requires authentication which is pre-validated by the {@link com.example.trainerapi.security.filters.JwtAuthenticationFilter}.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all workouts for the user.
     * @param auth The authorization header.
     * @return A list of workouts.
     */
    @GetMapping("/workouts")
    public ResponseEntity<?> getWorkouts(@RequestHeader("Authorization") String auth) {
        return userService.getWorkouts(auth);
    }


    @GetMapping("/{username}/workouts/")
    public ResponseEntity<?> getUserWorkouts(@PathVariable("username") String username) {
        System.out.println("Username: " + username);
        System.out.println("Username: " + username);
        System.out.println("Username: " + username);
        return ResponseEntity.ok(new ArrayList<>());
        // userService.getSharedWorkouts(username);
    }



    /**
     * Add a workout for the user. This always creates a new workout.
     * @param auth The authorization header.
     * @param workout The workout to add.
     * @return The added workout.
     */
    @PostMapping("/workouts")
    public ResponseEntity<?> addWorkout(@RequestHeader("Authorization") String auth,  @RequestBody Workout workout) {
        return userService.addWorkout(auth, workout);
    }

    /**
     * Delete a workout for the user.
     * @param auth The authorization header.
     * @param id The id of the workout to delete.
     * @return A 200 OK response.
     */
    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<?> deleteWorkout(@RequestHeader("Authorization") String auth, @PathVariable UUID id) {
        return userService.deleteWorkout(auth, id);
    }

    /**
     * Update a workout for the user.
     * @param auth The authorization header.
     * @param id The id of the workout to update.
     * @param workout The workout to update.
     * @return The updated workout.
     */
    @PutMapping("/workouts/{id}")
    public ResponseEntity<?> updateWorkout(@RequestHeader("Authorization") String auth, @PathVariable UUID id, @RequestBody Workout workout) {
        return userService.updateWorkout(auth, id, workout);
    }

    /**
     * Get all exercise types.
     * @param auth The authorization header.
     * @return A list of exercise types.
     */
    @GetMapping("/exercisetypes")
    public ResponseEntity<?> getExerciseTypes(@RequestHeader("Authorization") String auth) {
        return userService.getExerciseTypes(auth);
    }

    @PostMapping("/exercisetypes")
    public ResponseEntity<?> addExerciseType(@RequestHeader("Authorization") String auth, @RequestBody ExerciseType exerciseType) {
        return userService.addExerciseType(auth, exerciseType);
    }

    /**
     * Delete an exercise type.
     * @param id The id of the exercise type to delete.
     * @param auth The authorization header.
     * @return A 200 OK response.
     */
    @DeleteMapping("/exercisetypes/{id}")
    public ResponseEntity<?> deleteExerciseType(@RequestHeader("Authorization") String auth, @PathVariable UUID id) {
        return userService.deleteExerciseType(auth, id);
    }

}
