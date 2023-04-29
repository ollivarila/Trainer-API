package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.Workout;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository actions for workoutRepository
 */
@Repository
public interface WorkoutRepository extends CrudRepository<Workout, UUID> {

    /**
     * finds workouts by user id
     * @param userId
     * @return list of workouts
     */
    List<Workout> findByUserId(UUID userId);

    /**
     * Deletes a workout by id and user id
     * @param id the id of the workout
     * @param user the id of the user
     * @return the amount of deleted rows
     */
    @Transactional
    int deleteByIdAndUser_Id(UUID id, UUID user);

    /**
     * finds workouts by workout id and user id
     * @param id workut's id
     * @param user user's id
     * @return Optional container containing workut matching both id's or empty
     */
    Optional<Workout> findByIdAndUser_Id(UUID id, UUID user);

    /**
     * finds workouts of given user that are shared
     * @param shared boolean true
     * @param user id of the user
     * @return list of workouts for given user that are shared
     */
    List<Workout> findBySharedAndUser_Id(boolean shared, UUID user);

}
