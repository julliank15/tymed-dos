# Tymed-Dos Backend

Spring Boot 3.x backend (Java 21, **Gradle**) for the Tymed-Dos historical photo archive.

## Stack

Spring Web, Spring Data JPA, Validation, Actuator, PostgreSQL driver, Hibernate Spatial, Flyway.

## Setup

1. Start the database from the repo root (see root README):
   ```bash
   cp .env.example .env    # set POSTGRES_PASSWORD
   docker compose up -d
   ```
2. Export the same variables the Compose `.env` uses, then run (requires JDK 21):
   ```bash
   export $(grep -v '^#' ../.env | xargs)
   ./gradlew bootRun
   ```

Configuration (`src/main/resources/application.yml`) reads `POSTGRES_USER`, `POSTGRES_PASSWORD`,
`POSTGRES_DB`, `POSTGRES_PORT` and optionally `POSTGRES_HOST` (default `localhost`) and `SERVER_PORT` (default `8080`).

## Endpoints

- `GET /` - basic info
- `GET /actuator/health` - health, including the DB check

## Migrations

Flyway migrations live in `src/main/resources/db/migration`.

## Tests

```bash
./gradlew test
```
