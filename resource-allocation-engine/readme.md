# Resource-Allocation-Engine

Resource-Allocation-Engine is built upon Spring Boot 2.3.8. This service provides suggestions of resources to a platform named Quantum.

This service uses [CQRS](https://martinfowler.com/bliki/CQRS.html#:~:text=CQRS%20stands%20for%20Command%20Query,you%20use%20to%20read%20information.) pattern to seperate read model and write model

## Requirements

For building and running the application you need:

- [Java Version 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html)
- [Maven](https://maven.apache.org)
- [Neo4j:4.2.3](https://neo4j.com/)

## Libraries used

- Spring Boot 2.3.8.RELEASE
- Spring Web
- Spring Data Neo4j
- Eureka Discovery Client
- Config Client
- Prometheus
- Spring Boot Actuator
- Lombok
- Swagger API

## Installing the libraries

You can use the following command to install the necessary libraries

```shell
mvn install
```

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.stackroute.allocationengine` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
