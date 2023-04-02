package com.example.trainerapi.controllers.responses;

import com.example.trainerapi.models.entities.Exercise;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WorkoutResponse {
    private String id;
    private String name;
    private Date workoutStarted;
    private Date workoutEnded;

    private List<Exercise> exercises;
}
