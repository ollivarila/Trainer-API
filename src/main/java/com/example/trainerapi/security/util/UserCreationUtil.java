package com.example.trainerapi.security.util;

import com.example.trainerapi.models.entities.ExerciseType;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.repositories.ExerciseTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreationUtil {

    private final ExerciseTypeRepository exerciseTypeRepository;
    private static final String[] basicExercises = {
            "squat", "front squat", "bench press", "incline bench press",
            "dumbbell press", "deadlift", "romanian deadlift", "barbell row",
            "overhead press", "barbell curl", "dumbbell curl", "tricep extension"
    };
    @Autowired
    public UserCreationUtil(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    @Transactional
    public void createDefaultExerciseTypes(User user){
        for(String exerciseName : basicExercises){
            ExerciseType exerciseType = new ExerciseType(exerciseName);
            exerciseType.setUser(user);
            exerciseTypeRepository.save(exerciseType);
        }

    }
}
