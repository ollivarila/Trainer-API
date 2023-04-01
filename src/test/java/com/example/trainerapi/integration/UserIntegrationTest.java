package com.example.trainerapi.integration;

import com.example.trainerapi.integration.mock.MockHttpServletRequestBuilderFactory;
import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import com.example.trainerapi.security.util.JwtTokenUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
        workoutRepository.deleteAll();
    }

    @Test
    public void createsWorkout() throws Exception {
        createUser();
        createWorkout();
        Workout created = workoutRepository.findAll().iterator().next();
        assertEquals("test", created.getName());
    }

    private void createUser() throws Exception {
        mockMvc.perform(requestFactory.registerRequest("user", "password"));
    }

    private void createWorkout() throws Exception {
        String token = JwtTokenUtil.generate("user");
        Workout workout = new Workout();
        workout.setName("test");
        mockMvc.perform(requestFactory.createWorkoutRequest(token, workout))
                .andExpect(status().isOk());
    }
}
