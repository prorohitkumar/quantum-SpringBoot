FROM openjdk:11-jre-slim
WORKDIR profile/src
ADD ./target/profile-service-0.0.1-SNAPSHOT.jar /profile/src/profile-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","profile-service-0.0.1-SNAPSHOT.jar"]