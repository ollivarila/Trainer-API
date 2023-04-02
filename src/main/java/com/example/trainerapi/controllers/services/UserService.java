package com.example.trainerapi.controllers.services;

import com.example.trainerapi.models.entities.ExerciseType;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.models.repositories.ExerciseTypeRepository;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import com.example.trainerapi.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

    // todo send proper error messages if operations fail (e.g. workout not found)
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

    public ResponseEntity<?> deleteWorkout(String authHeader, UUID workoutId) {
        User user = getUserFromAuthHeader(authHeader);
        workoutRepository.deleteByIdAndUser_Id(workoutId, user.getId());
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


    public ResponseEntity<?> deleteExerciseType(String auth, UUID id) {
        User user = getUserFromAuthHeader(auth);
        exerciseTypeRepository.deleteByIdAndUser_Id(id, user.getId());
        return ResponseEntity.ok().build();
    }
}
