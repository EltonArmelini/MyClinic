FROM openjdk:23-ea-17-jdk-bullseye

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ARG JAR_FILE=target/tp-integrador-be-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

CMD ["./wait-for-it.sh", "database:3306", "--", "java", "-jar","app.jar"]

EXPOSE 8080