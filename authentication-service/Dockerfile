FROM openjdk:11-jre-slim
WORKDIR auth/src
ADD ./target/authentication-service-0.0.1-SNAPSHOT.jar /auth/src/authentication-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","authentication-service-0.0.1-SNAPSHOT.jar"]