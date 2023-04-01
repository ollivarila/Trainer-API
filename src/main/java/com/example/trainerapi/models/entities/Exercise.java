package com.example.trainerapi.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

// todo general description of entity and relations
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
