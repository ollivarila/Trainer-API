package com.example.trainerapi.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class ExerciseType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
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
