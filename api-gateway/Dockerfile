FROM openjdk:11-jre-slim
WORKDIR gateway/src
ADD ./target/api-gateway-0.0.1-SNAPSHOT.jar /gateway/src/api-gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","api-gateway-0.0.1-SNAPSHOT.jar"]