package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.ExerciseType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ExerciseTypeRepository extends CrudRepository<ExerciseType, UUID>{
    List<ExerciseType> findByUserId(UUID userId);

}
