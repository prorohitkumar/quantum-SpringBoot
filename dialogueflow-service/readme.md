# Dialogueflow-Service

DialogFlow-Service is built upon Spring Boot 2.3.8. This service provides profile updation chatbot function to a platform named Quantum.

## Requirements

For building and running the application you need:

- [Java Version 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html)
- [Maven](https://maven.apache.org)
- [DialogFlow: 2.0.0](https://cloud.google.com/dialogflow/)

## Libraries used

- Spring Boot 2.3.8.RELEASE
- Spring Web
- Eureka Discovery Client
- Config Client
- Prometheus
- Spring Boot Actuator
- Lombok
- Google Cloud DataFlow
- Google API Services Oauth2
- Eureka Discovery Client
- Config Client
- Logger
- Prometheus
- Spring Boot Actuator
- Swagger API

## Installing the libraries

You can use the following command to install the necessary libraries

```shell
mvn install
```

## Running the application locally


There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.stackroute.dialogueflow` class from your IDE.


Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
