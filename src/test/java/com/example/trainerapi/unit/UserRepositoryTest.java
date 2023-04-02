package com.example.trainerapi.unit;

import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    public void existTrainerByUsernameWorks() {
        userRepository.save(new User("admin", "password"));
        boolean exists = userRepository.existsTrainerUserByUsername("admin");
        boolean shouldNotExist = userRepository.existsTrainerUserByUsername("admin2");
        assertThat(exists).isTrue();
        assertThat(shouldNotExist).isFalse();
    }

    @Test
    public void findByUsernameWorks() {
        userRepository.save(new User("admin", "password"));
        User user = userRepository.findByUsername("admin");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("admin");
    }
}
