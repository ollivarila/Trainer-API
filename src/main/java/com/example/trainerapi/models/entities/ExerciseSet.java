package com.example.trainerapi.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ExerciseSet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double weight;
    private int reps;

    @Override
    public String toString(){
        return String.format("ExerciseSet[id=%s, reps='%s', weight='%s']", id, reps, weight);
    }
}
