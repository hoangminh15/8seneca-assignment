# Task Management Microservice

A Spring Boot microservice for managing users and categorized tasks (Bugs and Features). It provides full CRUD functionality, REST APIs, filtering, and database schema management via Liquibase.

## Technology Stack

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Liquibase
- Gradle
- JUnit 5, Mockito
- MockMVC (for integration testing)

## Build, Run, and Test

### Build the Project

```bash
./gradlew build
```

### Run the Application

```bash
./gradlew bootRun
```

Liquibase will automatically apply all migrations during startup.

### Test the Application

To run all unit and integration tests:

```bash
./gradlew test
```

Test coverage includes:
- Unit tests for `UserService` and `TaskService`
- Integration tests for `TaskController` and `UserController`

## API Endpoints

### User Endpoints

- POST `/users` – Create a user
- GET `/users/{id}` – Get user by ID
- GET `/users` – List all users
- PUT `/users/{id}` – Update user
- DELETE `/users/{id}` – Delete user

### Task Endpoints

- POST `/tasks` – Create a task (type = BUG or FEATURE)
- GET `/tasks/{id}` – Get task by ID
- GET `/tasks?status=OPEN&userId=1` – Filter tasks
- GET `/tasks/search?query=login` – Search tasks by text
- PUT `/tasks/{id}` – Update task
- DELETE `/tasks/{id}` – Delete task

## Database Migrations

Liquibase is used to manage schema changes. All changelogs are stored in:

```
src/main/resources/db/changelog/
```

Master changelog: `db.changelog-master.yaml`

Sub-changelogs:
- `db.changelog-user.yaml`
- `db.changelog-task.yaml`

Each contains structured changesets for `users`, `task`, `bug`, and `feature` tables.

## Design and Architecture

- **Domain Modeling**:
    - `Task` is an abstract entity.
    - `Bug` and `Feature` inherit from `Task` using `@Inheritance(strategy = JOINED)` to normalize the schema and enforce subtype-specific fields in dedicated tables.

- **DTO Pattern**:
    - Incoming requests use `TaskRequestDto`.
    - Outgoing responses use `TaskResponseDto` to prevent recursive entity serialization and decouple the API surface from internal entities.

- **Persistence**:
    - Spring Data JPA handles repository operations.
    - PostgreSQL is used as the relational database.
    - Liquibase is used for database schema versioning.

- **REST API Design**:
    - Standard CRUD operations exposed via REST.
    - Tasks can be filtered by status and user.
    - Optional text search endpoint allows keyword matching in `name` and `description`.

- **Error Handling**:
    - Global exception handler with `@ControllerAdvice` ensures consistent JSON error responses for validation and entity-not-found cases.

- **Testing**:
    - JUnit and Mockito for unit testing.
    - MockMVC for HTTP-level integration tests.

## Project Structure

```
com.example.senecaassignment
├── controllers
├── dtos
├── exceptions
├── models
├── repositories
└── services
```
