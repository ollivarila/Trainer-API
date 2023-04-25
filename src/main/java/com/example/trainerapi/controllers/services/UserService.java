package com.example.trainerapi.controllers.services;

import com.example.trainerapi.models.entities.ExerciseType;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.models.repositories.ExerciseTypeRepository;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import com.example.trainerapi.security.util.JwtTokenUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        int deletedAmount = workoutRepository.deleteByIdAndUser_Id(workoutId, user.getId());
        if(deletedAmount == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


    public ResponseEntity<?> addWorkout(String authHeader, Workout workout) {
        User user = getUserFromAuthHeader(authHeader);
        workout.setUser(user);
        workout.clearIds(); // We want to always create a new workout, so we clear the ids
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
        int deletedAmount = exerciseTypeRepository.deleteByIdAndUser_Id(id, user.getId());
        if(deletedAmount == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<?> updateWorkout(String auth, UUID id, Workout workout) {
        User user = getUserFromAuthHeader(auth);
        Optional<Workout> result = workoutRepository.findByIdAndUser_Id(id, user.getId());
        if(result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Workout updated = new Workout(result.get());
        updated.updateWith(workout);
        workoutRepository.save(updated);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<?> getSharedWorkouts(String name) {
        User user = userRepository.findByUsername(name);
        Optional<Workout> result = workoutRepository.findSharedAndUser_Id(true, user.getId());
        if(result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);

    }
}
