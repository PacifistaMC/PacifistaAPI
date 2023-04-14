FROM maven:3-openjdk-17 AS MAVEN

MAINTAINER Antoine PRONNIER, <antoine.pronnier@gmail.com>

WORKDIR /container/pacifista-api/

COPY pom.xml .

COPY client/pom.xml ./client/
COPY client/src ./client/src

COPY service/pom.xml ./service/
COPY service/src ./service/src

RUN mvn clean package -B
RUN rm service/target/pacifista-api-service-*-javadoc.jar
RUN rm service/target/pacifista-api-service-*-sources.jar

FROM openjdk:17 AS FINAL_PTEROQ

MAINTAINER Antoine PRONNIER, <antoine.pronnier@gmail.com>

USER container
ENV USER=container HOME=/home/container
WORKDIR /home/container

COPY --from=MAVEN /container/pacifista-api/service/target/pacifista-api-service-*.jar /home/java/server.jar

COPY ./entrypointPteroq.sh /entrypoint.sh

CMD ["/bin/bash", "/entrypoint.sh"]