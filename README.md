# Backend Task: Quota Management System

## Overview

This project implements a Spring Boot-based backend API to manage user quotas for two different resources, **Resource One** and **Resource Two**. The system enforces limits on API requests made by users, blocking them if they exceed their quota. The application also handles different database configurations, switching between MySQL and a mock database based on the time of day.

## Features

- **User Management**: APIs for creating, updating, deleting, and retrieving user information.
- **Quota Management**: Each user has a quota for two different resources:
  - **QuotaResourceOne** and **QuotaResourceTwo**.
  - The user can consume up to a predefined threshold of requests.
  - If the user exceeds the threshold, they are blocked from making further requests for the resource.
- **Time-based Database Switching**: Uses MySQL during the day (12 AM to 6 PM) and a mock database during the evening (6 PM to 12 AM).
- **Scalable Architecture**: Implements a clean, maintainable codebase with good OOP practices.

## Project Setup

### Prerequisites

- **Java 17+**
- **Maven** (for build and dependency management)
- **MySQL** (for the active database during the day)
- **Docker** (for running MySQL locally)

### Dependencies

- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL Driver
- H2 (for testing purposes)
- JUnit 5 for testing

### Steps to Set Up

1. **Clone the repository**:

   ```bash
   git clone https://github.com/your-username/backend-task.git
   cd backend-task
   ```

2. **Configure MySQL Database**:
   Ensure you have Docker installed and running. The MySQL database will run in Docker during the daytime.

   **Start MySQL in Docker**:
   ```bash
   docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql:8
   ```

   Then update the `application.properties` with the necessary MySQL configuration.

3. **Install Dependencies**:

   Make sure that all dependencies are resolved by Maven by running the following command:

   ```bash
   mvn clean install
   ```

4. **Run the Application**:

   You can run the application locally with:

   ```bash
   mvn spring-boot:run
   ```

   The application should now be running at `http://localhost:8080`.

### Configuration

- **MySQL Database Configuration**: The database will switch between MySQL and the mock database based on the time of day. The active database (either MySQL or NotARealDB) is configured in the `application.properties` file.

- **Port Configuration**: The default port is `8080`, but it can be changed in the `application.properties` file if needed.

## API Endpoints

### 1. **User Management APIs**

- **Create User**  
  `POST /api/users`  
  Request Body:
  ```json
  {
      "firstName": "John",
      "lastName": "Doe"
  }
  ```
  Response:
  ```json
  {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe"
  }
  ```

- **Get User by ID**  
  `GET /api/users/{id}`  
  Response:
  ```json
  {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe"
  }
  ```

- **Get All Users**  
  `GET /api/users`  
  Response:
  ```json
  [
      {
          "id": 1,
          "firstName": "John",
          "lastName": "Doe"
      }
  ]
  ```

- **Update User**  
  `PUT /api/users/{id}`  
  Request Body:
  ```json
  {
      "firstName": "John",
      "lastName": "Smith"
  }
  ```

- **Delete User**  
  `DELETE /api/users/{id}`

### 2. **Quota Management APIs**

- **Consume Resource One**  
  `POST /api/quota/resource-one/{userId}`  
  Increases the number of requests made by the user for Resource One.

- **Consume Resource Two**  
  `POST /api/quota/resource-two/{userId}`  
  Increases the number of requests made by the user for Resource Two.

- **Get Resource One Status**  
  `GET /api/quota/resource-one/{userId}`  
  Returns `true` if the user can still make requests for Resource One, `false` if blocked.

- **Get Resource Two Status**  
  `GET /api/quota/resource-two/{userId}`  
  Returns `true` if the user can still make requests for Resource Two, `false` if blocked.

### Example Responses:

- `GET /api/quota/resource-one/1`:  
  Response: `200 OK`  
  Body: `true` (if the user has not exceeded the quota)

- `GET /api/quota/resource-two/1`:  
  Response: `200 OK`  
  Body: `false` (if the user is blocked)

## Testing

Unit tests have been written using **JUnit 5** and **Mockito** to mock the service layer. You can run the tests with the following command:

```bash
mvn test
```

This will run all the tests, including controller and service tests, to ensure that the application behaves as expected.

### Test Coverage:

- **QuotaControllerTest**: Tests the endpoints for checking and consuming quota.
- **QuotaServiceTest**: Tests the business logic for managing quota consumption.
- **UserControllerTest**: Tests user management API endpoints.

## Database Switching Logic

- The system will **automatically switch between MySQL** (from 12 AM to 5:59 PM) and **NotARealDB** (from 6 PM to 11:59 PM) based on the time of day.
- **MySQL** is used during the daytime for actual data persistence.
- **NotARealDB** is a mock database used for the evening hours, returning simulated data.

## Troubleshooting

- **Database Connection Issues**: Make sure the MySQL container is running and the database connection settings are correctly configured in `application.properties`.
- **Service Not Starting**: Check the logs for any missing or incorrect configuration, and ensure that Maven dependencies are correctly installed.
