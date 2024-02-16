FROM openjdk:17-jdk
COPY ./build/libs/*.jar moiroomserver-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "moiroomserver-0.0.1-SNAPSHOT.jar"]