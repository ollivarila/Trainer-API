package com.example.trainerapi;

import com.example.trainerapi.models.entities.User;
import com.example.trainerapi.models.entities.Workout;
import com.example.trainerapi.models.repositories.UserRepository;
import com.example.trainerapi.models.repositories.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Entrypoint for the application
 */
// todo tässä kohtaa applikaatiossa ei tarvi @RequiredArgsConstructor
@SpringBootApplication
public class TrainerApiApplication {

	private static final Logger log = LoggerFactory.getLogger(TrainerApiApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TrainerApiApplication.class, args);
	}

	// todo @Autowired
	private final WorkoutRepository workoutRepository;


	/**
	 * Method for debugging
	 * @return Commandlinerunner that gets executed on startup
	 */
	public CommandLineRunner demo(UserRepository userRepository, ApplicationContext ctx) {
		return (args) -> {
			User user = new User("John","password");

			userRepository.save(user);

			log.info("Users found with findAll():");
			log.info("-------------------------------");
			for (User trainerUser : userRepository.findAll()) {
				log.info(trainerUser.toString());
			}

			Workout workout = new Workout();
			workout.setName("Workout 1");
			workout.setUser(user);

			workoutRepository.save(workout);

			log.info("Workouts: ");
			log.info("-------------------------------");

			User savedUser = userRepository.findById(user.getId()).get();


		};
	}

	/**
	 * Method for debugging BEANS
	 * @param ctx
	 */
	private void beans(ApplicationContext ctx){
		System.out.println("BEANS!!");
		String[] beanNames = ctx.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
		System.out.println("END BEANS!!");
	}

}
