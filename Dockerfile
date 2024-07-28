FROM amazoncorretto:21.0.4-al2023-headless

ARG JAR_FILE=targer/

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar","app.jar"]

EXPOSE 8080