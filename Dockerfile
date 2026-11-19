# Use an official, lightweight eclipse-temurin runtime image matching your Java 17 target
FROM eclipse-temurin:17-jre-alpine

# Set the active working directory inside the container
WORKDIR /app

# Copy the built JAR file using a relative path (NO LEADING SLASH)
COPY target/GeosiebenWorkDayApplication-0.0.1-SNAPSHOT.jar app.jar

# Open up port 8080 (the default port for Spring Boot web applications)
EXPOSE 8080

# Execute the Spring Boot application using the runtime execution array
ENTRYPOINT ["java", "-jar", "app.jar"]