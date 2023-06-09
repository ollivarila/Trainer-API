package com.example.trainerapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


/**
 * Entrypoint for the application
 */
@SpringBootApplication
public class TrainerApiApplication {

	private static final Logger log = LoggerFactory.getLogger(TrainerApiApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TrainerApiApplication.class, args);
	}

	/**
	 * Method for debugging
	 * @return CommandLineRunner that gets executed on startup
	 */
	public CommandLineRunner demo() {
		return (args) -> {
			log.info("STARTING THE APPLICATION");
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
