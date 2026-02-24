## Healthcare Appointment Management System (Backend)

A secure, role-based Healthcare Appointment Management backend built using Spring Boot.
This project demonstrates production-level backend architecture with authentication, authorization, business rule enforcement, and API documentation.

### Project Overview

This system allows:
- Citizens to book and manage appointments
- Doctors to view and update appointment status
- Role-based secure access using JWT
- Controlled appointment state transitions
The focus of this project is backend architecture, security, and clean business logic implementation.

### Architecture

The project follows layered architecture:

Controller → Service → Repository → Database

#### Layers:
- Controller Layer
-- Handles API endpoints and request validation
- Service Layer
-- Contains business rules and state transition logic
- Repository Layer
-- JPA-based database access
- Security Layer
-- JWT authentication & role-based authorization

## Authentication & Authorization
Implemented using:
- Spring Security
- JWT (JSON Web Token)
- Stateless session management
### Roles:
- CITIZEN
- DOCTOR

## Appointment State Rules
The system enforces strict business rules:
### Possible States:
- BOOKED
- CANCELLED
- COMPLETED
### Rules Implemented:
- Cancelled appointments cannot be updated
- Completed appointments cannot be modified
- Only BOOKED appointments can be cancelled
- Doctors can only modify their own appointments
- Citizens can only cancel their own appointments

This ensures data integrity and real-world workflow consistency.

## Tech Stack
- Java 17
- Spring Boot
- Spring Security
- JWT (jjwt)
- Spring Data JPA (Hibernate)
- MySQL
- Swagger (springdoc-openapi)
- Maven
