# Use a Maven image with Java 17
FROM maven:3.8.4-openjdk-17-slim

# Set the working directory for the Java code
WORKDIR /usr/src/java-code

# Copy the entire current directory to the Docker image
COPY . /usr/src/java-code/

# Build the Maven project
RUN mvn package

# Set the working directory for the Java application
WORKDIR /usr/src/java-app

# Copy the packaged JAR file to the app directory
RUN cp /usr/src/java-code/target/*.jar ./app.jar

# Expose port 8080 to the outside world
EXPOSE 8081

# Command to run the Java application
CMD ["java", "-jar", "app.jar"]
