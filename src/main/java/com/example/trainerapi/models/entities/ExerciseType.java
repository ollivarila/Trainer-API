package com.example.trainerapi.models.entities;

import jakarta.persistence.*;

@Entity
public class ExerciseType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    protected ExerciseType() {}

    public ExerciseType(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("ExerciseType[id=%s, name='%s']", id, name);
    }
}
