package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.ExerciseType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExerciseTypeRepository extends CrudRepository<ExerciseType, UUID>{

    List<ExerciseType> findByUserId(UUID userId);

    @Transactional
    void deleteByIdAndUser_Id(UUID id, UUID user);
}
