# Spring-Security

## Features

This project demonstrates the following functionalities:

1. **SPRING-SECURITY** - API request filtering based on user roles
2. **Token-based authentication** - JWT tokens with 5-minute expiration
3. **Docker support** - Application can be containerized

## API Documentation

### 1. Register User

**Method:** `POST`

**Endpoint:** `http://localhost:8080/demo-api/auth/register`

**Request Body:**
```json
{
  "username": "john",
  "password": "john123",
  "role": "USER"
}
```

**Valid Roles:** `USER`, `ADMIN`

---

### 2. Generate Token (Login)

**Method:** `POST`

**Endpoint:** `http://localhost:8080/demo-api/auth/login`

**Request Body:**
```json
{
  "username": "john",
  "password": "john123"
}
```

---

### 3. List Users

**Method:** `GET`

**Endpoint:** `http://localhost:8080/demo-api/ven-users`

**Authorization:** Admin only (`ADMIN` role required)

---

### 4. List Todos

**Method:** `GET`

**Endpoint:** `http://localhost:8080/demo-api/ven-todos`

**Authorization:** Available to both `ADMIN` and `USER` roles

---

## Getting Started

### Prerequisites
- Java 17+
- Maven
- Docker (optional)

### Running the Application
```bash
# Clone the repository
git clone https://github.com/surendrary/Spring-Security

# Navigate to project directory
cd user-todos-main
  
# Run with Maven
./mvnw spring-boot:run -Dstart-class=com.security.demo.todos.UserTodosApplication

# Or with Docker
docker build -t spring-security-demo .
docker run -p 8080:8080 spring-security-demo
```

## Authentication Flow

1. Register a new user using the `/auth/register` endpoint
2. Login with credentials to receive a JWT token via `/auth/login`
3. Include the token in the `Authorization` header for protected endpoints:
```
   Authorization: Bearer <your-token>
```
4. Tokens expire after 5 minutes - request a new token if needed
