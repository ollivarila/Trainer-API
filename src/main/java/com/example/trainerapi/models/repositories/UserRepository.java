package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    boolean existsTrainerUserByUsername(String username);

    User findByUsername(String username);
}
