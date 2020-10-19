
FROM gradle:6.8.0-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon
FROM openjdk:8
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/trip-story-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/trip-story-0.0.1-SNAPSHOT.jar"]