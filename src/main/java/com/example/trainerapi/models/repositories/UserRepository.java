package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository action for userRepository
 */
@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    /**
     * checks if given user exists in the repository
     * @param username
     * @return true of false based on if the user exists in the repository
     */
    boolean existsTrainerUserByUsername(String username);

    /**
     * finds user by username
     * @param username
     * @return user matching username
     */
    User findByUsername(String username);

}
