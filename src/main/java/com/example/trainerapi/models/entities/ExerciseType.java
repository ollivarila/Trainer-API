package com.example.trainerapi.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

// todo general description of entity and relations
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
