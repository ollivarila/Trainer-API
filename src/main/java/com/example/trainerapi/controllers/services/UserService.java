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

import java.util.*;

/**
 * handles methods called by usercontroller and manyuserscontroller
 */

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


    /**
     * gets all workouts for the user
     * @param authHeader authorization header
     * @return list of workouts containing all workouts of the user
     */
    public ResponseEntity<?> getWorkouts(String authHeader) {
        User user = getUserFromAuthHeader(authHeader);
        return ResponseEntity.ok(workoutRepository.findByUserId(user.getId()));
    }

    /**
     * deletes workout by id
     * @param authHeader authorization header
     * @param workoutId id of the workout to be deleted
     * @return return code 200
     */
    public ResponseEntity<?> deleteWorkout(String authHeader, UUID workoutId) {
        User user = getUserFromAuthHeader(authHeader);
        int deletedAmount = workoutRepository.deleteByIdAndUser_Id(workoutId, user.getId());
        if(deletedAmount == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


    /**
     * adds workout for the user sending the request
     * @param authHeader authorization header
     * @param workout workout to be added
     * @return added workout
     */
    public ResponseEntity<?> addWorkout(String authHeader, Workout workout) {
        User user = getUserFromAuthHeader(authHeader);
        workout.setUser(user);
        workout.clearIds(); // We want to always create a new workout, so we clear the ids
        workoutRepository.save(workout);
        return ResponseEntity.ok(workout);
    }

    /**
     * gets all exercisetypes for the user
     * @param auth authorization header
     * @return list of exercise types
     */
    public ResponseEntity<?> getExerciseTypes(String auth) {
        User user = getUserFromAuthHeader(auth);
        return ResponseEntity.ok(exerciseTypeRepository.findByUserId(user.getId()));
    }

    /**
     * adds exercisetype for user sending the request
     * @param auth authorization header
     * @param exerciseType exercisetype to be added
     * @return added exercise type
     */
    public ResponseEntity<?> addExerciseType(String auth, ExerciseType exerciseType) {
        User user = getUserFromAuthHeader(auth);
        exerciseType.setUser(user);
        exerciseTypeRepository.save(exerciseType);
        return ResponseEntity.ok(exerciseType);
    }

    /**
     * fetches user from the authorization header
     * @param header authorization header
     * @return User
     */

    private User getUserFromAuthHeader(String header){
        String token = header.substring(7);
        String username = JwtTokenUtil.getUsername(token);
        return userRepository.findByUsername(username);
    }


    /**
     * deletes exercisetype from dp
     * @param auth authorization header
     * @param id id for the exercisetype to be deleted
     * @return return code 200
     */
    public ResponseEntity<?> deleteExerciseType(String auth, UUID id) {
        User user = getUserFromAuthHeader(auth);
        int deletedAmount = exerciseTypeRepository.deleteByIdAndUser_Id(id, user.getId());
        if(deletedAmount == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * updates workout
     * @param auth authorization header
     * @param id id of the workout to update
     * @param workout the new workout
     * @return updated workout
     */
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

    /**
     * gets shared workouts of given user
     * @param name username of the user we want the workouts from
     * @return list of shared workuts of the user given
     */
    public ResponseEntity<?> getSharedWorkouts(String name) {
        User user = userRepository.findByUsername(name);
        List<Workout> result = workoutRepository.findBySharedAndUser_Id(true, user.getId());
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);

    }

    /**
     * gets all usernames from database
     * @return list of all usernames from database
     */

    public ResponseEntity<?> getAllUsers() {
        Iterable<User> result = userRepository.findAll();
        List<String> users = new ArrayList<>();
        Iterator<User> it = result.iterator();

        while (it.hasNext()){
            users.add(it.next().getUsername());
        }

        if(users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);

    }


}
