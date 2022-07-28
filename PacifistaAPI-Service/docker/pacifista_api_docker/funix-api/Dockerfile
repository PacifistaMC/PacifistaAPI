FROM openjdk:17 AS FINAL

ARG FUNIX_API_VERSION

WORKDIR /container/java

RUN curl "https://repo1.maven.org/maven2/fr/funixgaming/api/server/funix-api-server/${FUNIX_API_VERSION}/funix-api-server-${FUNIX_API_VERSION}.jar" -o server.jar

ENTRYPOINT ["java", "-jar", "/container/java/server.jar", "-Dspring.profiles.active=docker"]
