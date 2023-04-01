package com.example.trainerapi.integration;

import com.example.trainerapi.integration.mock.MockHttpServletRequestBuilderFactory;
import com.example.trainerapi.models.entities.ExerciseType;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.models.repositories.ExerciseTypeRepository;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import com.example.trainerapi.security.util.JwtTokenUtil;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private MockHttpServletRequestBuilderFactory requestFactory;

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    @AfterEach
    public void afterEach() {
        exerciseTypeRepository.deleteAll();
        workoutRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createsWorkout() throws Exception {
        createUser("user");
        createWorkout("user");
        Workout created = workoutRepository.findAll().iterator().next();
        assertThat(created.getName()).isEqualTo("test");
    }

    private void createUser(String username) throws Exception {
        mockMvc.perform(requestFactory.registerRequest(username, "password"));
    }

    private void createWorkout(String username) throws Exception {
        String token = JwtTokenUtil.generate(username);
        Workout workout = new Workout();
        workout.setName("test");
        mockMvc.perform(requestFactory.createWorkoutRequest(token, workout))
                .andExpect(status().isOk());
    }

    @Test
    public void getsAllWorkouts() throws Exception {
        createUser("user");
        createWorkout("user");
        createWorkout("user");
        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.getAllWorkoutsRequest(token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void getsCorrectWorkoutsWhenTwoUsersExist() throws Exception {
        createUser("user");
        createUser("user2");
        createWorkout("user");
        createWorkout("user2");
        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.getAllWorkoutsRequest(token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void deletesWorkout() throws Exception {
        createUser("user");
        createWorkout("user");
        User user = userRepository.findByUsername("user");
        Workout created = workoutRepository.findByUserId(user.getId()).get(0);
        System.out.println("CREATED WORKOUT");
        System.out.println(created);
        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.deleteWorkoutRequest(token, created.getId()))
                .andExpect(status().isOk());
        Iterable<Workout> workouts = workoutRepository.findAll();
        boolean shouldBeFalse = workouts.iterator().hasNext();
        assertThat(shouldBeFalse).isFalse();
    }

    @Test
    public void deletesAll(){
        Workout workout = new Workout();
        workout.setName("test");
        workoutRepository.save(workout);
        workoutRepository.findAll();
        assertThat(workoutRepository.findAll().iterator().hasNext()).isTrue();

        workoutRepository.deleteAll();
        assertThat(workoutRepository.findAll().iterator().hasNext()).isFalse();
    }

    @Test
    public void createsExerciseType() throws Exception {
        createUser("user");
        createExerciseType();
        User user = userRepository.findByUsername("user");
        List<ExerciseType> exerciseTypes = exerciseTypeRepository.findByUserId(user.getId());
        assertThat(exerciseTypes.size()).isEqualTo(1);
        ExerciseType created = exerciseTypes.get(0);
        assertThat(created.getName()).isEqualTo("test");
    }

    private void createExerciseType() throws Exception {
        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.createExerciseTypeRequest(token, "test"))
                .andExpect(status().isOk());
    }

    @Test
    public void getsAllExerciseTypes() throws Exception {
        createUser("user");
        createExerciseType();
        createExerciseType();
        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.getAllExerciseTypesRequest(token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }



}
