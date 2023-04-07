package com.example.trainerapi.integration;

import com.example.trainerapi.integration.mock.MockHttpServletRequestBuilderFactory;
import com.example.trainerapi.models.entities.Exercise;
import com.example.trainerapi.models.repositories.ExerciseTypeRepository;
import com.example.trainerapi.models.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseTypeRepository exerciseRepository;

    @Autowired
    private MockHttpServletRequestBuilderFactory requestFactory;

    @AfterEach
    public void afterEach() {
        exerciseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void registerUser() throws Exception {
        mockMvc.perform(requestFactory.registerRequest("test", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.startsWith("eyJ")));
    }

    @Test
    public void registerWithExistingUsername() throws Exception {
        createUser();
        mockMvc.perform(requestFactory.registerRequest("user", "password"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void authenticateUser() throws Exception {
        createUser();
        mockMvc.perform(requestFactory.authenticateRequest("user", "password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.startsWith("eyJ")));
    }

    private void createUser() throws Exception {
        mockMvc.perform(requestFactory.registerRequest("user", "password"));
    }

    @Test
    public void authenticateUserWithWrongPassword() throws Exception {
        createUser();
        mockMvc.perform(requestFactory.authenticateRequest("user", "wrong"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authenticateUserWithWrongUsername() throws Exception {
        createUser();
        mockMvc.perform(requestFactory.authenticateRequest("wrong", "password"))
                .andExpect(status().isUnauthorized());
    }
}
