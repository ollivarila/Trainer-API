package com.example.trainerapi.controllers.services;

import com.example.trainerapi.models.entities.ExerciseType;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.models.repositories.ExerciseTypeRepository;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import com.example.trainerapi.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    public UserService(UserRepository userRepository, WorkoutRepository workoutRepository, ExerciseTypeRepository exerciseTypeRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public ResponseEntity<?> getWorkouts(String authHeader) {
        User user = getUserFromAuthHeader(authHeader);
        return ResponseEntity.ok(workoutRepository.findByUserId(user.getId()));
    }

    public ResponseEntity<?> deleteWorkout(UUID workoutId) {
        System.out.println("Deleting workout: " + workoutId);
        workoutRepository.deleteById(workoutId);
        return ResponseEntity.ok().build();
    }


    public ResponseEntity<?> addWorkout(String authHeader, Workout workout) {
        User user = getUserFromAuthHeader(authHeader);
        workout.setUser(user);
        workoutRepository.save(workout);
        return ResponseEntity.ok(workout);
    }

    public ResponseEntity<?> getExerciseTypes(String auth) {
        User user = getUserFromAuthHeader(auth);
        return ResponseEntity.ok(exerciseTypeRepository.findByUserId(user.getId()));
    }

    public ResponseEntity<?> addExerciseType(String auth, ExerciseType exerciseType) {
        User user = getUserFromAuthHeader(auth);
        exerciseType.setUser(user);
        exerciseTypeRepository.save(exerciseType);
        return ResponseEntity.ok(exerciseType);
    }

    private User getUserFromAuthHeader(String header){
        String token = header.substring(7);
        String username = JwtTokenUtil.getUsername(token);
        return userRepository.findByUsername(username);
    }
}
