package com.example.trainerapi.controllers.services;

import com.example.trainerapi.controllers.responses.WorkoutResponse;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import com.example.trainerapi.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;

    public ResponseEntity<?> getWorkouts(String authHeader) {
        String username = parseUsername(authHeader);
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        return ResponseEntity.ok(user.getWorkouts());
    }

    public ResponseEntity<?> deleteWorkout(String authHeader, String workoutId) {
        String username = parseUsername(authHeader);
        User user = userRepository.findByUsername(username);
        List<Workout> workouts = user.getWorkouts();
        user.setWorkouts(removeWorkout(workouts, workoutId));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    private List<Workout> removeWorkout(List<Workout> workouts, String workoutId) {
        return workouts
                .stream()
                .filter(workout -> !workout.getId().equals(workoutId))
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> addWorkout(String authHeader, Workout workout) {
        String username = parseUsername(authHeader);
        User user = userRepository.findByUsername(username);
        workout.setUser(user);
        workoutRepository.save(workout);
        return ResponseEntity.ok(workout);
    }

    private String parseUsername(String authHeader) {
        String token = authHeader.substring(7);
        return JwtTokenUtil.getUsername(token);
    }

    public ResponseEntity<?> getExerciseTypes(String auth) {
        String username = parseUsername(auth);
        User user = userRepository.findByUsername(username);
        return ResponseEntity.ok(user.getExerciseTypes());
    }
}
