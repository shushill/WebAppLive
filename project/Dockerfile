# Use an official JDK runtime as a parent image
FROM openjdk:17-jdk-slim

ARG SPRINGBOOT_IMAGE_NAME=springboot-image
ARG SPRINGBOOT_IMAGE_TAG=latest

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file from the target directory into the container at /app
COPY target/ROOT.jar /app/ROOT.jar

# Make port 8080 available to the world outside this container
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "ROOT.jar"]
