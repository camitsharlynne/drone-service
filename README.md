# drone-service
A Java-based web service exposing REST API for drone medication as load for delivery.

---

## Description
“The Drone” is a new technology company which is working in the area of drone-based delivery for
urgent package delivery in locations with difficult access. The company has a fleet of 10 drones. A
drone is capable of delivering small load (payload) additionally to its navigation and cameras.
For this use case, the load is medications.

---

## Building the application

To generate required files for OpenAPI/Swagger,

Open Terminal, and run command:
`` 
mvn clean install
``

## Running the application

`` 
mvn spring-boot:run
``

## Testing the application

* For Postman, collections can be imported from the directory:
`postman-collection`

* Alternatively, you may use Swagger UI to test. Once run, navigate to the Swagger UI:
`http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/`

---

## Technologies
1. Java 17
2. Spring Boot
3. Swagger/OpenAPI 3
4. H2 Database
