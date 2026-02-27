## Healthcare Appointment Management System (Backend)

A secure, role-based Healthcare Appointment Management backend built using Spring Boot.
This project demonstrates production-level backend architecture with authentication, authorization, business rule enforcement, and API documentation.

## Project Overview

This system allows:
- Citizens to book and manage appointments
- Doctors to view and update appointment status
- Role-based secure access using JWT
- Controlled appointment state transitions
The focus of this project is backend architecture, security, and clean business logic implementation.

## Architecture

The project follows layered architecture:
```
Controller → Service → Repository → Database
```

### Layers:
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
### Access Control:
```
Endpoint Type	                Access Role
-------------------------------------------
Book Appointment	              CITIZEN
View Own Appointments	          CITIZEN
Cancel Own Appointment	          CITIZEN
View Doctor Appointments	      DOCTOR
Update Appointment Status	      DOCTOR
```

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

## API Documentation
Swagger UI available at:
```
http://localhost:8081/swagger-ui/index.html
```
All APIs can be tested directly through Swagger.

## Project Structure
```
Healthcare_Appointment_System/
├── HealthcareAppBE/                 # Backend (Spring Boot + JWT + JPA)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── healthcare/
│   │   │   │           └── healthcare_system/
│   │   │   │               ├── controller/     # REST Controllers
│   │   │   │               ├── service/        # Business logic
│   │   │   │               ├── repository/     # JPA Repositories
│   │   │   │               ├── model/          # Entities
│   │   │   │               ├── dto/            # Request/Response DTOs
│   │   │   │               ├── security/       # JWT Filter + JWT Utils
│   │   │   │               ├── config/         # Security configuration
│   │   │   │               ├── exception/      # Custom exceptions + handler
│   │   │   │               └── HealthcareSystemApplication.java
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── ...
│   │   └── test/                                 # (future unit tests)
│   ├── pom.xml
│   └── README_BE.md                              # (optional backend-specific readme)
│
└── HealthcareAppFE/              # (Future Frontend – React / etc.)
    ├── src/
    ├── package.json
    └── README_FE.md
```
## Setup Instructions 
1. Clone Repository
2. Configure MySQL in application.properties
3. Run
```
mvn clean install
```
4. Start application
5. Open Swagger UI

## Key Implemented Features
- JWT-based authentication
- Role-based endpoint protection
- Global exception handling
- Custom business exceptions
- Appointment state machine logic
- Pagination for doctor appointments
- DTO pattern for clean API responses
- Validation using @Valid

## Future Improvements
- Frontend integration (React)
- Deployment (Render / AWS / Railway)
- Email notifications
- Admin role
- Unit & Integration tests
- Docker support

## Learning Outcome 
This project demonstrates:
- Secure API development
- Clean architecture principles
- Business rule enforcement
- Real-world state management
- RESTful API design
- Production-ready error handling
