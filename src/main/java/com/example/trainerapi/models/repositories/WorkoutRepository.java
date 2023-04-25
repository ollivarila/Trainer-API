package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.Workout;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkoutRepository extends CrudRepository<Workout, UUID> {

    List<Workout> findByUserId(UUID userId);

    /**
     * Deletes a workout by id and user id
     * @param id the id of the workout
     * @param user the id of the user
     * @return the amount of deleted rows
     */
    @Transactional
    int deleteByIdAndUser_Id(UUID id, UUID user);

    Optional<Workout> findByIdAndUser_Id(UUID id, UUID user);

    List<Workout> findBySharedAndUser_Id(boolean shared, UUID user);

}
