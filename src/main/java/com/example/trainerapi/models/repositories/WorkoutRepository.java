package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.Workout;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkoutRepository extends CrudRepository<Workout, String> {

    List<Workout> findByUserId(UUID userId);

    @Transactional
    void deleteByIdAndUser_Id(UUID id, UUID user);
}
