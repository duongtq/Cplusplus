FROM openjdk:8-jdk-alpine
MAINTAINER duong.tq.dev@gmail.com

WORKDIR /opt/app

ARG JAR_FILE=target/todojdbc-0.0.1-SNAPSHOT.jar

# cp spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

