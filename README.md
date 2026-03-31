# Healthcare Appointment Management System

Production-style full-stack healthcare appointment platform with role-based access for **Citizens (Patients)** and **Doctors**.

## ✅ Stable Release
This repository is now frozen as **v1.0.0 (stable baseline)** for portfolio/resume use.

> Status: Deployed and working with role-based login and appointment workflows.

---

## Live Deployment
- **Frontend:** https://healthcare-system-backend-1.onrender.com
- **Backend API:** https://healthcare-system-backend-t79z.onrender.com
- **Health Check:** `GET /api/health`

---

## Key Features

### Authentication & Authorization
- JWT-based authentication
- Role-based route protection (`CITIZEN`, `DOCTOR`)
- Stateless Spring Security config

### Citizen Capabilities
- Login/register
- Book appointment
- View own appointments
- Cancel booked appointments

### Doctor Capabilities
- Login
- View assigned appointments (paginated)
- Update appointment status

### Business Rules Enforced
- Completed appointments cannot be modified
- Cancelled appointments cannot be updated
- Citizens can manage only their own appointments
- Doctors can update only their assigned appointments

---

## Tech Stack

### Backend
- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL / MySQL compatible datasource config
- JWT (jjwt)
- Swagger / OpenAPI

### Frontend
- React + Vite
- Axios
- React Router

### Deployment
- Render (frontend + backend + managed Postgres)

---

## Architecture

```text
Frontend (React/Vite)
   ↓ HTTPS (JWT)
Backend (Spring Boot)
   ↓ JPA/Hibernate
PostgreSQL (Render)
```

Layering:
```text
Controller → Service → Repository → Database
```

---

## Demo Credentials (Seeded)
For demo/testing on deployed app:
- **Citizen:** `kabir@test.com` / `secret1234`
- **Doctor:** `dr.sharma@test.com` / `doctor123`

> These users are seeded/updated at startup for demo reliability.

---

## API Highlights

### Public
- `POST /api/auth/login`
- `POST /api/auth/register`
- `GET /api/health`

### Citizen-only
- `POST /api/appointments`
- `GET /api/appointments/my`
- `PATCH /api/appointments/{id}/cancel`

### Doctor-only
- `GET /api/appointments/doctor/{doctorEmail}`
- `PATCH /api/appointments/{id}/status`

Swagger UI (local):
- `http://localhost:8081/swagger-ui/index.html`

---

## Environment Variables

### Backend
- `PORT` (Render auto-injects)
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `APP_CORS_ALLOWED_ORIGINS`

### Frontend
- `VITE_API_BASE_URL`
- `VITE_API_TIMEOUT_MS` (optional; defaults to 60000ms)

---

## Local Development

### Backend
```bash
mvn clean install
mvn spring-boot:run
```

### Frontend
```bash
cd HealthcareAppFE
npm install
npm run dev
```

---

## Resume-ready Impact Points
- Built and deployed a secure, role-based healthcare platform on Render.
- Implemented JWT auth with Spring Security and protected API routes by role.
- Designed appointment state workflow with real-world business constraints.
- Integrated React frontend with production backend and environment-based configs.
- Troubleshot deployment issues across CORS, DB, auth, and cold-start behavior.

---

## Roadmap (Next Iterations)
- Automated tests (unit + integration)
- Admin role and management panel
- Better UI/UX polish
- Notifications/email integration
- CI/CD pipeline and quality gates
