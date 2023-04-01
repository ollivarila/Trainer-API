package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.Workout;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutRepository extends CrudRepository<Workout, UUID> {

    List<Workout> findByUserId(UUID userId);

}
