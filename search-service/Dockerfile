FROM openjdk:11-jre-slim
WORKDIR search/src
ADD ./target/search-service-0.0.1-SNAPSHOT.jar /search/src/search-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","search-service-0.0.1-SNAPSHOT.jar"]