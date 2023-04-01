package com.example.trainerapi.controllers;

import com.example.trainerapi.controllers.services.UserService;
import com.example.trainerapi.models.entities.ExerciseType;
import com.example.trainerapi.models.entities.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for user related endpoints.
 * Requires authentication which is pre-validated by the {@link com.example.trainerapi.security.filters.JwtAuthenticationFilter}.
 */
@RestController
@RequestMapping("/api/user")
// todo ei tarvi @RequiredArgsConstructor
public class UserController {

    // todo @Autowired
    private final UserService userService;

    /**
     * Get all workouts for the user.
     * @param auth The authorization header.
     * @return A list of workouts.
     */
    @GetMapping("/workouts")
    public ResponseEntity<?> getWorkouts(@RequestHeader("Authorization") String auth) {
        return userService.getWorkouts(auth);
    }

    /**
     * Add a workout for the user.
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
