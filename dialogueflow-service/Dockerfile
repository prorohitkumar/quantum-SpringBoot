FROM openjdk:11-jre-slim
WORKDIR dialogueflow/src
ADD ./target/dialogueflow-service-0.0.1-SNAPSHOT.jar /dialogueflow/src/dialogueflow-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","dialogueflow-service-0.0.1-SNAPSHOT.jar"]