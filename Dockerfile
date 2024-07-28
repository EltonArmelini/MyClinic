FROM amazoncorretto:21.0.4-al2023-headless

ARG JAR_FILE=target/tp-integrador-be-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar","app.jar"]

EXPOSE 8080