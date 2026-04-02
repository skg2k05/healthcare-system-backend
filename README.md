## # Healthcare Appointment System API

A secure, role-based # Healthcare Appointment System API built using Spring Boot.
This project demonstrates production-level backend architecture with authentication, authorization, business rule enforcement, and API documentation.

## Project Overview

This backend API allows:
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
List Doctors	                  AUTHENTICATED USER
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
- Deployment ( AWS / Railway)
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

### Default Seed Login Users
On startup, the app seeds/updates these login users:
- `kabir@test.com` / `secret1234` (role: `CITIZEN`)
- `dr.sharma@test.com` / `doctor123` (role: `DOCTOR`)

## Render Deployed Link 
https://healthcare-system-backend-1.onrender.com

---

## Render Deployment Notes (Backend + Frontend)

If login works locally but fails on Render, verify the following:

### Backend (Web Service)
- Start command: `./mvnw spring-boot:run` (or use a Docker deploy if preferred)
- Required environment variables:
  - `DB_URL` (Render PostgreSQL **External Database URL** / JDBC format)
  - `DB_USERNAME`
  - `DB_PASSWORD`
  - `APP_CORS_ALLOWED_ORIGINS` (comma-separated list, include frontend URL)
  - `PORT` (Render sets this automatically)

### Frontend (Static Site / Web Service)
- `VITE_API_BASE_URL=https://<your-backend-service>.onrender.com`
- Optional: `VITE_API_TIMEOUT_MS=60000` (helps with free-tier cold starts)

### Quick Validation Checklist
1. Open `https://<backend-url>/api/health` and confirm it responds.
2. In browser DevTools > Network, confirm login request hits:
   `https://<backend-url>/api/auth/login`
3. Confirm backend CORS allows your exact frontend domain.
4. Confirm user actually exists in the deployed database.
   
---

## Stable Release Note
- Baseline freeze: `v1.0.0` (see `CHANGELOG.md`).
