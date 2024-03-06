FROM openjdk:17-jdk-alpine
ADD target/todotask-0.0.1-SNAPSHOT.jar docker-app.jar
ENTRYPOINT ["java", "-jar", "docker-app.jar"]