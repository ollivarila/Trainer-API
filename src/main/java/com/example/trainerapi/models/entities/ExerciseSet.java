package com.example.trainerapi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


/**
 * Represents a set of an exercise. Set contains reps and weight. <br>
 * <b>ExerciseSet</b> has a <b>ManyToOne</b> relation to <b>Exercise</b>, meaning that one exercise can have many sets. <br>
 */
@Data
@NoArgsConstructor
@Entity
public class ExerciseSet implements Clearable {

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
    @JsonIgnore
    private Exercise exercise;

    @Override
    public String toString(){
        return String.format("ExerciseSet[id=%s, reps='%s', weight='%s']", id, reps, weight);
    }

    @Override
    public void clearIds() {
        id = null;
    }
}
