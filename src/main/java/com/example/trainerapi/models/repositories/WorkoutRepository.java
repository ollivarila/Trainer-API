package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.Workout;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

// todo lisää @Repository
public interface WorkoutRepository extends CrudRepository<Workout, String> {
    List<Workout> findByUserId(UUID userId);

}
