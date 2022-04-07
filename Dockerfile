FROM openjdk:8-jre-alpine

ARG VERSION=0.0.1-SNAPSHOT

WORKDIR /app/
COPY target/demo-library-${VERSION}.jar demo-library.jar

CMD java -jar demo-library.jar
