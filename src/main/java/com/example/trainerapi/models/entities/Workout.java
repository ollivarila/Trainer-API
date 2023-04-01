package com.example.trainerapi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

// todo sä et välttämättä tarvi näitä kaikkia annotaatiotoita @Entity on tärkein jos tämä on sun db taulu
@Getter
@Setter
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
