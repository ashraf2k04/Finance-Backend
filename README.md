# 🚀 Finance Backend API

A robust and secure backend system built using Spring Boot, designed to manage users, roles, and financial records with authentication and authorization.

## 📌 Features

- 🔐 JWT-based Authentication & Authorization
- 👥 Role-Based Access Control (ADMIN, USER)
- 📊 Dashboard Summary (Income, Expense, Net Balance)
- 🧾 CRUD Operations for Records
- 🧠 Clean Architecture (Controller → Service → Repository)
- 🛡️ Spring Security with Method-Level Authorization
- 📄 Swagger/OpenAPI Documentation with JWT Support
- ⚙️ Centralized Exception Handling
- ✅ DTO Validation
- 🗃️ JPA & Hibernate integration

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Lombok
- Swagger (springdoc-openapi)

## 🧩 Project Structure

```
com.assignment.zorvyn
│
├── config
├── controller
├── dto
├── entity
├── repository
├── service
├── security
├── util
```

## 🔐 Authentication Flow

1. User logs in via `/auth/login`
2. Server returns JWT token
3. Client sends token in header:

```
Authorization: Bearer <token>
```

4. Token is validated by Spring Security
5. Access granted based on roles

## 🔑 API Endpoints

### Auth APIs
```
POST /auth/register  - To register new user either by admin or unregister user only
POST /auth/login  - To login register user
GET  /auth/me  - To get logged in user data
```

### User APIs (Admin only)
```
GET /users
POST /users
PATCH /users/{id}/role
PATCH /users/{id}/status
DELETE /users/{id}
```

### Record APIs
```
POST /records
GET /records
GET /records/{id}
PUT /records/{id}
DELETE /records/{id}
```

### Dashboard APIs
```
GET /dashboard/summary
GET /dashboard/trends
GET /dashboard/category
```

## 📊 Sample Response

```JSON
  On SUCCESS - 
{
  "message": "Login successful",
  "error": false,
  "content": {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkB6b3J2eW4uY29tIiwicm9sZSI6IkFETUlOIiwiaWF0IjoxNzc1NDg5MDk1LCJleHAiOjE3NzU1NzU0OTV9.zKtZPwyXah5pnXHhBI5PgwCo0moS6mxGx9tCjr55bHI",
    "user": {
      "id": 1,
      "name": "Admin",
      "email": "admin@zorvyn.com",
      "role": "ADMIN",
      "status": "ACTIVE"
    }
  }
}
  on ERROR -
{
  "message": "Authentication required",
  "error": true,
  "content": null
}

```

## ⚙️ Configuration

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/zorvyn
    username: root
    password: password

jwt:
  secret: your-secret-key
  expiration: 86400000

openapi:
  title: Zorvyn API
  version: v1
  description: API documentation
```

## 📄 Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```


**Note:** Use the **Authorize** button to add your JWT token.

## 🛡️ Security

**Public Endpoints:**
```
/auth/**
```

**Protected Endpoints:**
```
/users/**     → ADMIN only
/records/**   → AUTHENTICATED users
/dashboard/** → AUTHENTICATED users
```

## ❗ Exception Handling

Handles the following exceptions gracefully:

* 400 Bad Request
* 401 Unauthorized
* 403 Forbidden
* 404 Not Found
* 500 Internal Server Error

## ✅ Validation Strategy

* DTO → Validated using Jakarta Validation
* Entity → Minimal validation
* Service → Business logic validation
* Database → Constraints & indexes

## 🚀 Run Locally

```bash
git clone <repository-url>
cd zorvyn
./mvnw spring-boot:run
```

## 🧪 Testing

* Swagger UI
* Postman

**Testing Flow:**

1. Register a new user (`/auth/register`)
2. Login (`/auth/login`)
3. Copy the JWT token
4. Use the token in Authorization header for protected routes

## 📌 Future Improvements

* Refresh tokens
* Pagination & Sorting
* Docker support
* CI/CD pipeline
* Rate limiting
* Audit logging

---

## 👨‍💻 Author

**Ashraf Ali**
