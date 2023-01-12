FROM openjdk:11-jre-slim
WORKDIR engine/src
ADD ./target/resource-allocation-engine-0.0.1-SNAPSHOT.jar /engine/src/resource-allocation-engine-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","resource-allocation-engine-0.0.1-SNAPSHOT.jar"]