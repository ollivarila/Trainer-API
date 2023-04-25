package com.example.trainerapi.integration.mock;

import com.example.trainerapi.models.entities.ExerciseType;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

    public RequestBuilder getAllWorkoutsRequest(String token) {
        return get("/api/user/workouts")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public RequestBuilder getSharedWorkouts(String token, String userName) {
        return get(String.format("/api/user/%s/workouts", userName))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public RequestBuilder createExerciseTypeRequest(String token, String name) {
        ExerciseType exerciseType = new ExerciseType(name);
        String json = createJson(exerciseType);

        return post("/api/user/exercisetypes")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
    }

    public RequestBuilder getAllExerciseTypesRequest(String token) {
        return get("/api/user/exercisetypes")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public RequestBuilder deleteWorkoutRequest(String token, UUID id) {
        return delete("/api/user/workouts/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public RequestBuilder deleteExerciseTypeRequest(String token, UUID id) {
        return delete("/api/user/exercisetypes/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public RequestBuilder updateWorkoutRequest(String token, Workout workout) {
        String json = createJson(workout);
        return put("/api/user/workouts/" + workout.getId())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
    }

    public RequestBuilder refreshTokenRequest(String token){
        return post("/api/auth/refresh")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }
}
