package com.example.trainerapi.unit;

import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void afterEach() {
        workoutRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void repositoryLoads() {
        assertNotNull(workoutRepository);
    }

    @Test
    public void findByUserId() {
        User user = new User("John","password");
        userRepository.save(user);
        Workout workout = new Workout();
        workout.setUser(user);
        workoutRepository.save(workout);

        Workout foundWorkout = workoutRepository.findByUserId(user.getId()).get(0);
        assertEquals(workout.getId(), foundWorkout.getId());
    }

    @Test
    public void deletesAll(){
        Workout workout = new Workout();
        workout.setName("test");
        workoutRepository.save(workout);
        workoutRepository.findAll();
        assertTrue(workoutRepository.findAll().iterator().hasNext());

        workoutRepository.deleteAll();
        assertFalse(workoutRepository.findAll().iterator().hasNext());
    }
}
