FROM openjdk:11-jre-slim
WORKDIR nlp/src
ADD ./target/nlp-service-0.0.1-SNAPSHOT.jar /nlp/src/nlp-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","nlp-service-0.0.1-SNAPSHOT.jar"]