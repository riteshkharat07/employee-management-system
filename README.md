# Employee Management System



A Microservices-based Employee Management System built using Java, Spring Boot, MySQL, JWT Security, Apache Kafka, Eureka Service Discovery, API Gateway, Config Server, and Docker.



## Architecture

```text
                    API Gateway
                         |
              +----------+----------+
              |                     |
              v                     v
        Auth Service         Employee Service
              |                     |
              v                     v
           User DB              Employee DB
                                    |
                                    v
                               Apache Kafka
                                    |
                                    v
                          Notification Service





Microservices

1\. Employee Service



Handles employee management operations.



Features:



Employee CRUD

Search employees

Pagination

Sorting

DTO-based API responses

Validation

Global exception handling

JWT authentication

Role-based authorization



2\. Auth Service



Handles authentication and authorization.



Features:

User registration

User login

BCrypt password encryption

JWT token generation

Role-based access control

ADMIN and USER roles



3\. API Gateway



Acts as the single entry point for client requests and routes requests to appropriate microservices using service discovery.



4\. Service Registry



Implemented using Netflix Eureka for service registration and discovery.



5\. Config Server



Centralizes configuration for microservices.



6\. Notification Service



Consumes employee events from Apache Kafka and processes employee event messages asynchronously.



Kafka Flow

Employee Service

&#x20;     |

&#x20;     v

Kafka Producer

&#x20;     |

&#x20;     v

employee-events topic

&#x20;     |

&#x20;     +----------------------+

&#x20;     |                      |

&#x20;     v                      v

Employee Consumer     Notification Service



When a new employee is created, Employee Service publishes an employee event to Kafka. Notification Service consumes the event asynchronously.



Technologies Used:

Java

Spring Boot

Spring Data JPA

Hibernate

MySQL

Spring Security

JWT

BCrypt

Spring Cloud Gateway

Netflix Eureka

Spring Cloud Config Server

Apache Kafka

Docker

Docker Compose

Maven / Gradle

API Endpoints

Authentication

POST /auth/register

POST /auth/login

Employee

POST   /employees

GET    /employees

GET    /employees/{id}

PUT    /employees/{id}

DELETE /employees/{id}



GET /employees/search?keyword=

GET /employees/page?page=0\&size=5

GET /employees/sort?sortBy=salary\&direction=desc



Role-Based Authorization:

ADMIN



Can access:



GET employees

POST employee

PUT employee

DELETE employee

Search

Pagination

Sorting



USER



Can access:



GET all employees

GET employee by ID



Ports:



Service	Port

API Gateway	8080

Auth Service	8082

Employee Service	8083

Notification Service	8084

Config Server	8888

Eureka Server	8761

Kafka	9092 / 29092



Running the Project:



Start Docker Services



From the project root:



docker compose up -d



This starts:



Service Registry

Config Server

Auth Service

Employee Service

Kafka

Run API Gateway



Run ApiGatewayApplication from IntelliJ.



Run Notification Service



Run NotificationServiceApplication from IntelliJ.



Verify Eureka



Open:



http://localhost:8761



Registered services should appear as UP.



Testing Flow:

Login

&#x20; ↓

Receive JWT Token

&#x20; ↓

Call Employee APIs through API Gateway

&#x20; ↓

Employee Service processes request

&#x20; ↓

Employee event published to Kafka

&#x20; ↓

Notification Service consumes event



Project Highlights:



Microservices architecture

Centralized configuration

Service discovery with Eureka

API Gateway routing

JWT authentication and role-based authorization

Kafka-based asynchronous communication

Dockerized services

Employee CRUD with search, pagination and sorting



Author

Ritesh Kharat

