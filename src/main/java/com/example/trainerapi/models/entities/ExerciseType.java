package com.example.trainerapi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


/**
 * Represents the name of the exercise i.e Bench press. Exercise type contains the name of the exercise. <br>
 * <b>ExerciseType</b> has a <b>ManyToOne</b> relation to <b>User</b>, meaning that one user can create many exercise types. <br>
 * <b>ExerciseType</b> has a <b>OneToMany</b> relation to <b>Exercise</b>, meaning that one exercise type can be used in many exercises. <br>
 */
@Data
@Entity
public class ExerciseType {

    /**
     * Exercise type id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Exercise type name
     */
    private String name;

    /**
     * User who created the exercise type.
     * User can have many exercise types, thus ManyToOne
     */
    @ManyToOne
    @JsonIgnore
    private User user;

    public ExerciseType() {
    }

    public ExerciseType(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("ExerciseType[id=%s, name='%s']", id, name);
    }
}
