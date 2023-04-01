package com.example.trainerapi.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private ExerciseType exerciseType;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ExerciseSet> sets;

    public Exercise(ExerciseType exerciseType, List<ExerciseSet> sets) {
        this.exerciseType = exerciseType;
        this.sets = sets;
    }
}
