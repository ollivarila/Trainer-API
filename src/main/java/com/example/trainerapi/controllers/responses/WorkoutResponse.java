package com.example.trainerapi.controllers.responses;

import com.example.trainerapi.models.entities.Exercise;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

// todo @Builder tekee builder patternilla objekteja. Tuo @Data on vähän vaihtoehtoinen sille, eli molemmille ei välttämättä tarvetta.
// kannattaa tutustua factory ja builder konsepteihin, jos ei ole vielä tuttuja. Builderiä näkee aika paljon uudemmissa java kirjastoissa.
@Data
@Builder
public class WorkoutResponse {
    private String id;
    private String name;
    private Date workoutStarted;
    private Date workoutEnded;

    private List<Exercise> exercises;
}
