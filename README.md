# Todo Microservices

A microservices-based Todo List application built with Java and Spring Boot.

## Architecture

The application consists of the following microservices:

- **Eureka Server**: Service discovery server
- **API Gateway**: Routes requests to appropriate microservices
- **User Service**: Handles user registration, authentication, and management
- **Todo Service**: Manages todo items (CRUD operations)

## Technology Stack

- Java 17
- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- H2 Database (local database for development)
- Maven

## Getting Started

1. Clone the repository
2. Build the project: `mvn clean install`
3. Start services in order:
   - Eureka Server
   - User Service
   - Todo Service
   - API Gateway

## Project Status

This project is under active development. Track progress through GitHub Issues.