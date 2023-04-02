package com.example.trainerapi.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Represents an exercise created by user. Exercise contains it's exerciseType also known as the name of the exercise and a list of sets. <br>
 * <b>Exercise</b> has a <b>ManyToOne</b> relation to <b>ExerciseType</b>, meaning that one exercise type can be used in many exercises. <br>
 * <b>Exercise</b> has a <b>OneToMany</b> relation to <b>ExerciseSet</b>, meaning that one exercise can have many sets. <br>
 * <b>Exercise</b> has a <b>ManyToOne</b> relation to <b>Workout</b>, meaning that one workout can have many exercises. <br>
 */
@Data
@NoArgsConstructor
@Entity
public class Exercise {

    /**
     * Exercise id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Exercise type
     * Exercise can have one exercise type and many exercises can have the same exercise type, thus ManyToOne
     */
    @ManyToOne
    private ExerciseType exerciseType;

    /**
     * List of sets in the exercise.
     * Exercise can have many sets, thus OneToMany
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<ExerciseSet> sets;
}
