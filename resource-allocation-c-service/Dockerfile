FROM openjdk:11-jre-slim
WORKDIR rsr/src
ADD ./target/resource-allocation-c-service-0.0.1-SNAPSHOT.jar /rsr/src/resource-allocation-c-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","resource-allocation-c-service-0.0.1-SNAPSHOT.jar"]