FROM openjdk:23-ea-17-jdk-bullseye

ARG JAR_FILE=target/tp-integrador-be-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

CMD ["java", "-jar","app.jar"]

EXPOSE 8080
