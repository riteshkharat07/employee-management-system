# Employee Management System

A Microservices-based Employee Management System built using Java, Spring Boot, MySQL, Spring Security, JWT, Apache Kafka, Netflix Eureka, API Gateway, Config Server, and Docker.

## Architecture

```text
                           Client / Postman
                                  |
                                  v
                           +--------------+
                           | API Gateway  |
                           |    :8080     |
                           +--------------+
                                  |
                    +-------------+-------------+
                    |                           |
                    v                           v
             +-------------+              +---------------+
             | Auth        |              | Employee      |
             | Service     |              | Service       |
             |   :8082     |              |    :8083      |
             +-------------+              +---------------+
                    |                           |
                    v                           v
                +--------+                 +----------+
                | User DB|                 |Employee DB|
                +--------+                 +----------+
                                                 |
                                                 v
                                          +-------------+
                                          | Apache Kafka |
                                          | employee-    |
                                          | events       |
                                          +-------------+
                                                 |
                                                 v
                                      +----------------------+
                                      | Notification Service |
                                      |        :8084         |
                                      +----------------------+

        +-------------------+       +----------------------+
        | Service Registry  |       |    Config Server     |
        |   Eureka :8761    |       |      :8888           |
        +-------------------+       +----------------------+
                  |                           |
                  +-----------+---------------+
                              |
                     Service Discovery
                     & Centralized Config

Project Overview:
This project demonstrates a microservices-based Employee Management System where different business responsibilities are separated into independent services.

The system provides employee management APIs, authentication and authorization using JWT, service discovery using Eureka, centralized configuration using Config Server, asynchronous communication using Apache Kafka, and containerization using Docker.

Microservices:

1. Employee Service
Responsible for employee management operations.

Features:
Create employee
Get all employees
Get employee by ID
Update employee
Delete employee
Search employees
Pagination
Sorting
DTO-based responses
Request validation
Global exception handling
JWT authentication
Role-based authorization

2. Auth Service
Responsible for user authentication and authorization.

Features:
User registration
User login
BCrypt password encryption
JWT token generation
Role-based access control
ADMIN and USER roles

3. API Gateway

Acts as the single entry point for client requests.

It routes requests to the appropriate microservice using Spring Cloud Gateway and Eureka service discovery.

Routes:
/auth/**       -> AUTH-SERVICE
/employees/**  -> EMPLOYEE-SERVICE

4. Service Registry
Implemented using Netflix Eureka.

It provides service registration and discovery so that microservices can locate each other dynamically.

5. Config Server
Provides centralized configuration for the microservices.

Configuration is maintained separately for:
api-gateway
auth-service
employee-service

This avoids maintaining configuration separately in every service.

6. Notification Service
Consumes employee events published by Employee Service through Apache Kafka.

Currently, the service receives and processes employee events asynchronously.

Kafka Communication:
The project uses Apache Kafka for asynchronous communication between Employee Service and Notification Service.

Event Flow:

Employee Service
       |
       v
 Kafka Producer
       |
       v
employee-events topic
       |
       v
Kafka Broker
       |
       v
Notification Service
       |
       v
Kafka Consumer

When a new employee is created:

1. Employee Service saves employee data
2. Employee Service creates an EmployeeEvent
3. Event is published to employee-events topic
4. Kafka stores the event
5. Notification Service consumes the event
6. Notification Service processes the employee event

Technologies Used - Technology	Purpose

Java 21	          - Programming Language
Spring Boot       -	Backend Framework
Spring Data JPA   -	Database Access
Hibernate         -	ORM
MySQL             -	Relational Database
Spring Security   - Security
JWT	              - Authentication
BCrypt            - Password Encryption
Spring Cloud Gateway -	API Gateway
Netflix Eureka    -	Service Discovery
Spring Cloud Config	- Centralized Configuration
Apache Kafka      -	Asynchronous Communication
Docker            -	Containerization
Docker Compose    -	Multi-container Deployment
Maven             -	Build Tool
Gradle	          -Build Tool

Role-Based Authorization:
ADMIN

ADMIN users can access:
Get all employees
Get employee by ID
Create employee
Update employee
Delete employee
Search employees
Pagination
Sorting

USER
USER users can access:
Get all employees
Get employee by ID

Restricted operations return 403 Forbidden.

API Endpoints:
Authentication APIs:
POST /auth/register
POST /auth/login

Employee APIs:
POST   /employees
GET    /employees
GET    /employees/{id}
PUT    /employees/{id}
DELETE /employees/{id}

Search:
GET /employees/search?keyword=Ritesh

Pagination:
GET /employees/page?page=0&size=5

Sorting:
GET /employees/sort?sortBy=salary&direction=desc

Ports         -     Service	Port
API Gateway	  -      8080
Auth Service	-      8082
Employee Service  -  8083
Notification Service -8084
Config Server    -  	8888
Eureka Server    -  	8761
Kafka	           -    9092 / 29092
MySQL	           -    3306

Docker Architecture:

Docker Compose is used to run the core infrastructure and services.
Dockerized Services:
Service Registry
Config Server
Auth Service
Employee Service
Kafka

API Gateway and Notification Service can be run from IntelliJ during development.

Running the Project:
Prerequisites:

Make sure the following are installed:
Java 21
MySQL
Docker Desktop
IntelliJ IDEA / Spring Tool Suite


Postman:
1. Start Docker Desktop

Make sure Docker Desktop is running.

2. Start Docker Services

Open the project root terminal and run:

docker compose up -d

Check running containers:

docker ps

3. Run API Gateway

Run:

ApiGatewayApplication
from IntelliJ.

API Gateway runs on:
http://localhost:8080

4. Run Notification Service

Run:
NotificationServiceApplication
from IntelliJ.

Notification Service runs on:
http://localhost:8084

5. Verify Eureka

Open:
http://localhost:8761

Registered services should appear with status:
UP

Expected services include:
API-GATEWAY
AUTH-SERVICE
EMPLOYEE-SERVICE
Configuration and Security

Sensitive values such as database passwords and JWT secrets should be provided through environment variables and should not be committed to the repository.

Example:

spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}

Do not commit real passwords, JWT secrets, API keys, or other credentials to GitHub.

Testing Flow
1. User Login
       |
       v
2. JWT Token Generated
       |
       v
3. Client Sends Bearer Token
       |
       v
4. API Gateway
       |
       v
5. Employee Service
       |
       v
6. Employee CRUD Operation
       |
       v
7. Employee Event Published
       |
       v
8. Apache Kafka
       |
       v
9. Notification Service Consumes Event

Project Highlights:

Microservices-based architecture
RESTful APIs using Spring Boot
Employee CRUD operations
Search, pagination, and sorting
JWT authentication
Role-based authorization using ADMIN and USER roles
BCrypt password encryption
Service discovery using Eureka
Centralized configuration using Config Server
API Gateway for request routing
Apache Kafka for asynchronous communication
Notification Service for event consumption
Docker and Docker Compose for containerization
MySQL with JPA/Hibernate

Project Structure:

employee-management-system
|
+-- api-gateway
|
+-- auth-service
|
+-- config-server
|
+-- employee-service
|
+-- notification-service
|
+-- service-registry
|
+-- docker-compose.yml
|
+-- README.md

Author
Ritesh Kharat
