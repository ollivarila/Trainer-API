package com.example.trainerapi.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


// todo general description of entity and relations
@Data
@NoArgsConstructor
@Entity
public class ExerciseSet {

    /**
     * Exercise set id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    /**
     * Weight of the set
     */
    private double weight;
    /**
     * Number of reps in the set
     */
    private int reps;

    /**
     * Exercise that the set belongs to
     * Set can belong to one exercise, thus ManyToOne
     */
    @ManyToOne
    private Exercise exercise;

    @Override
    public String toString(){
        return String.format("ExerciseSet[id=%s, reps='%s', weight='%s']", id, reps, weight);
    }
}
