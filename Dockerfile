FROM openjdk:17.0.2-oracle

RUN mkdir /app

# Copy the built application
COPY ./build/libs/TrainerAPI-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app
# Expose the port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-Xmx220", "-jar", "app.jar"]