package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.ExerciseType;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository operations for Exercisetypes
 */

@Repository
public interface ExerciseTypeRepository extends CrudRepository<ExerciseType, UUID>{

    /**
     * finds exercisetypes by user id
     * @param userId user id
     * @return list of exercisetypes matching user id
     */
    List<ExerciseType> findByUserId(UUID userId);

    /**
     * Deletes an exercise type by id and user id
     * @param id the id of the exercise type
     * @param user the id of the user
     * @return the number of deleted rows
     */
    @Transactional
    int deleteByIdAndUser_Id(UUID id, UUID user);
}
