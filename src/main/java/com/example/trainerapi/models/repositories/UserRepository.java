package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    boolean existsTrainerUserByUsername(String username);

    User findByUsername(String username);
}
