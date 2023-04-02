package com.example.trainerapi.unit;

import com.example.trainerapi.models.entities.ExerciseType;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.repositories.ExerciseTypeRepository;
import com.example.trainerapi.models.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ExerciseTypeRepositoryTest {

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void afterEach() {
        exerciseTypeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void findByUserId() {
        User user = new User("John","password");
        userRepository.save(user);
        ExerciseType exerciseType = new ExerciseType("test");
        exerciseType.setUser(user);
        exerciseTypeRepository.save(exerciseType);

        ExerciseType foundExerciseType = exerciseTypeRepository.findByUserId(user.getId()).get(0);
        assertThat(exerciseType.getId()).isEqualTo(foundExerciseType.getId());
    }

    @Test
    public void deleteByIdAndUserId(){
        User user = new User("John","password");
        userRepository.save(user);
        ExerciseType exerciseType = new ExerciseType("test");
        exerciseType.setUser(user);
        exerciseTypeRepository.save(exerciseType);

        ExerciseType foundExerciseType = exerciseTypeRepository.findByUserId(user.getId()).get(0);
        assertThat(exerciseType.getId()).isEqualTo(foundExerciseType.getId());

        exerciseTypeRepository.deleteByIdAndUser_Id(exerciseType.getId(), user.getId());
        List<ExerciseType> types = exerciseTypeRepository.findByUserId(user.getId());
        assertThat(types.size()).isEqualTo(0);
    }
}
