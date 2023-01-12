FROM openjdk:11-jre-slim
WORKDIR project/src
ADD ./target/project-service-0.0.1-SNAPSHOT.jar /project/src/project-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","project-service-0.0.1-SNAPSHOT.jar"]