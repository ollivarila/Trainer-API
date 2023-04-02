package com.example.trainerapi.models.repositories;

import com.example.trainerapi.models.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    boolean existsTrainerUserByUsername(String username);
    User findByUsername(String username);
}
