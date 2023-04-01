package com.example.trainerapi.integration.mock;

import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.requestbody.LoginRequest;
import com.example.trainerapi.requestbody.RegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@NoArgsConstructor
public class MockHttpServletRequestBuilderFactory {

    @Autowired
    private ObjectMapper objectMapper;

    public MockHttpServletRequestBuilder registerRequest(String username, String password) {
        RegisterRequest registerRequest = new RegisterRequest(username, password);
            String json = createJson(registerRequest);
            return post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
    }

    private String createJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public RequestBuilder authenticateRequest(String user, String password) {
        LoginRequest loginRequest = new LoginRequest(user, password);
        String json = createJson(loginRequest);
        return post("/api/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
    }

    public RequestBuilder createWorkoutRequest(String token, Workout workout) {
        String json = createJson(workout);
        return post("/api/user/workouts")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
    }
}
