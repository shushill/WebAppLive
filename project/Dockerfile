
FROM openjdk:17-jdk-slim


WORKDIR /app

COPY target/ROOT.jar /app/ROOT.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "ROOT.jar"]
