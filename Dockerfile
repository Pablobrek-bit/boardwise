FROM openjdk:17-jdk-slim

WORKDIR /usr/app

COPY target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["java", "-jar", "app.jar"]
