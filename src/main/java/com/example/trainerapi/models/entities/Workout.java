package com.example.trainerapi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 *  Represents a workout created by user. Workout contains name, startDate, endDate, isPreset and a list of exercises. <br>
 *  <b>Workout</b> has a <b>ManyToOne</b> relation to <b>User</b>, meaning that one user can have many workouts. <br>
 *  <b>Workout</b> has a <b>OneToMany</b> relation to <b>Exercise</b>, meaning that one workout can have many exercises. <br>
 */
@NoArgsConstructor
@Data
@Entity
public class Workout {

    /**
     * Workout id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Workout name
     */
    private String name;

    /**
     * Workout start date
     */
    private Date workoutStarted;

    /**
     * Workout end date
     */
    private Date workoutEnded;

    /**
     * Whether the workout is a preset workout or not
     */
    private boolean isPreset;

    /**
     * User who created the workout.
     * User can have many workouts, thus ManyToOne
     */
    @JsonIgnore
    @ManyToOne
    private User user;

    /**
     * List of exercises in the workout.
     * Workout can have many exercises, thus OneToMany
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Exercise> exercises;
    @Override
    public String toString(){
        return String.format("Workout[id=%s, name='%s']", id, name);
    }
}
