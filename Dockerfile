FROM openjdk:17


RUN mkdir /app
WORKDIR /app

# Copy the built application
COPY  ./build/libs/TrainerAPI-0.0.1-SNAPSHOT.jar  app.jar

# Expose the port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]