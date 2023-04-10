FROM maven:3-openjdk-17 AS MAVEN

MAINTAINER Antoine PRONNIER, <antoine.pronnier@gmail.com>

WORKDIR /container/pacifista-api/

COPY pom.xml .

COPY PacifistaAPI-Client/pom.xml ./PacifistaAPI-Client/
COPY PacifistaAPI-Client/src ./PacifistaAPI-Client/src

COPY PacifistaAPI-Service/pom.xml ./PacifistaAPI-Service/
COPY PacifistaAPI-Service/src ./PacifistaAPI-Service/src

RUN mvn clean package

FROM openjdk:17 AS FINAL

WORKDIR /container/java

COPY --from=MAVEN /container/pacifista-api/PacifistaAPI-Service/target/pacifista-api-service-*.jar /container/java/server.jar

ENTRYPOINT ["java", "-jar", "/container/java/server.jar", "-Dspring.profiles.active=docker"]