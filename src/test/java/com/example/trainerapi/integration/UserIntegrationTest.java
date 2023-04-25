package com.example.trainerapi.integration;

import com.example.trainerapi.integration.mock.MockHttpServletRequestBuilderFactory;
import com.example.trainerapi.models.entities.Exercise;
import com.example.trainerapi.models.entities.ExerciseType;
import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.models.repositories.ExerciseTypeRepository;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import com.example.trainerapi.security.util.JwtTokenUtil;
import org.assertj.core.api.Condition;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// todo more tests
@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private MockHttpServletRequestBuilderFactory requestFactory;

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    @AfterEach
    public void afterEach() {
        exerciseTypeRepository.deleteAll();
        workoutRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createsWorkout() throws Exception {
        createUser("user");
        createWorkout("user");
        Workout created = workoutRepository.findAll().iterator().next();
        assertThat(created.getName()).isEqualTo("test");
    }

    private void createUser(String username) throws Exception {
        mockMvc.perform(requestFactory.registerRequest(username, "password"));
    }

    private void createWorkout(String username) throws Exception {
        String token = JwtTokenUtil.generate(username);
        Workout workout = new Workout();
        workout.setName("test");
        mockMvc.perform(requestFactory.createWorkoutRequest(token, workout))
                .andExpect(status().isOk());
    }

    @Test
    public void getsAllWorkouts() throws Exception {
        createUser("user");
        createWorkout("user");
        createWorkout("user");

        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.getAllWorkoutsRequest(token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));



    }

    @Test
    public void getsSharedWorkouts() throws Exception {
        createUser("user");
        createWorkout("user");
        createWorkout("user");

        Iterator<Workout> workouts = workoutRepository.findAll().iterator();
        while (workouts.hasNext()){
            Workout w = workouts.next();
            w.setShared(true);
            workoutRepository.save(w);
        }

        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.getSharedWorkouts(token, "user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));


    }

    @Test
    public void getsCorrectWorkoutsWhenTwoUsersExist() throws Exception {
        createUser("user");
        createUser("user2");
        createWorkout("user");
        createWorkout("user2");
        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.getAllWorkoutsRequest(token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void deletesWorkout() throws Exception {
        createUser("user");
        createWorkout("user");
        User user = userRepository.findByUsername("user");
        Workout created = workoutRepository.findByUserId(user.getId()).get(0);
        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.deleteWorkoutRequest(token, created.getId()))
                .andExpect(status().isOk());
        Iterable<Workout> workouts = workoutRepository.findAll();
        boolean shouldBeFalse = workouts.iterator().hasNext();
        assertThat(shouldBeFalse).isFalse();
    }

    @Test
    public void deletesAll(){
        Workout workout = new Workout();
        workout.setName("test");
        workoutRepository.save(workout);
        workoutRepository.findAll();
        assertThat(workoutRepository.findAll().iterator().hasNext()).isTrue();

        workoutRepository.deleteAll();
        assertThat(workoutRepository.findAll().iterator().hasNext()).isFalse();
    }

    @Test
    public void createsExerciseType() throws Exception {
        createUser("user");
        createExerciseType();
        User user = userRepository.findByUsername("user");
        List<ExerciseType> exerciseTypes = exerciseTypeRepository.findByUserId(user.getId());
        assertThat(exerciseTypes.size()).isEqualTo(13);
        ExerciseType created = exerciseTypes.stream().filter(exerciseType -> exerciseType.getName().equals("test")).findFirst().get();
        assertThat(created.getName()).isEqualTo("test");
    }

    private void createExerciseType() throws Exception {
        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.createExerciseTypeRequest(token, "test"))
                .andExpect(status().isOk());
    }

    @Test
    public void getsAllExerciseTypes() throws Exception {
        createUser("user");
        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.getAllExerciseTypesRequest(token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(12)));
    }

    @Test
    public void deletesExerciseType() throws Exception {
        createUser("user");
        createExerciseType();
        User user = userRepository.findByUsername("user");
        ExerciseType created = exerciseTypeRepository.findByUserId(user.getId()).get(0);
        String token = JwtTokenUtil.generate("user");
        mockMvc.perform(requestFactory.deleteExerciseTypeRequest(token, created.getId()))
                .andExpect(status().isOk());
        boolean exists = exerciseTypeRepository.existsById(created.getId());
        assertThat(exists).isFalse();
    }

    @Test
    public void createsNewWorkout() throws Exception {
        createUser("user");
        createWorkout("user");
        Workout created = workoutRepository.findAll().iterator().next();
        created.setPreset(true);
        Exercise exercise = new Exercise();
        created.getExercises().add(exercise);
        mockMvc.perform(requestFactory.createWorkoutRequest(JwtTokenUtil.generate("user"), created))
                .andExpect(status().isOk());

        User user = userRepository.findByUsername("user");

        List<Workout> workouts = workoutRepository.findByUserId(user.getId());
        Condition<Workout> condition = new Condition<>(Workout::isPreset, "preset");
        assertThat(workouts.size()).isEqualTo(2);
        assertThat(workouts).areAtMost(1, condition);
    }

    @Test
    public void updatesWorkout() throws Exception {
        createUser("user");
        createWorkout("user");
        Workout created = workoutRepository.findAll().iterator().next();
        created.setName("updated");
        created.setPreset(true);

        mockMvc.perform(requestFactory.updateWorkoutRequest(JwtTokenUtil.generate("user"), created))
                .andExpect(status().isOk());

        Workout workout = workoutRepository.findById(created.getId()).get();
        assertThat(workout.getName()).isEqualTo("updated");
        assertThat(workout.isPreset()).isTrue();
    }
}
