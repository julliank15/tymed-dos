# Tymed-Dos Backend

Spring Boot 3 backend (Java 21) for the Tymed-Dos historical photo archive, backed by PostgreSQL 16 + PostGIS.

## Requirements

- **JDK 21**: Amazon Corretto 21 recommended. The build fails with a clear message on older JDKs.
- **Docker**, for the database
- Maven is **not** required; use the bundled `./mvnw`

## Run locally

1. Start the database from the repo root (see the root README for details):
   ```bash
   cp .env.example .env    # set POSTGRES_PASSWORD
   docker compose up -d
   ```
2. Start the backend:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```
3. Open:
   - API: http://localhost:8080
   - Health (includes the DB check): http://localhost:8080/actuator/health
   - Swagger UI: http://localhost:8080/docs
   - OpenAPI JSON: http://localhost:8080/openapi

## Configuration

Database settings come from the **repo-root `.env`** (the same file Docker Compose uses), or from real environment variables, which win:

| Variable | Default | Notes |
|---|---|---|
| `POSTGRES_HOST` | `localhost` | |
| `POSTGRES_PORT` | `5432` | |
| `POSTGRES_DB` | `tymed_dos` | |
| `POSTGRES_USER` | `tymed` | |
| `POSTGRES_PASSWORD` | none | Required. Startup fails if it's missing. |

`SPRING_DATASOURCE_URL` / `_USERNAME` / `_PASSWORD` also work and override everything.

## API (current)

- `GET /`: API info
- `GET /health`: simple liveness check
- `GET /api/photos`: list photos
  - `start_date`, `end_date`: ISO date (`1920-01-01`) or date-time
  - `lat`, `lng`, `radius`: radius in km; all three together or none
  - `limit` (1–500, default 100), `offset` (default 0)
- `GET /api/photos/{id}`: one photo, 404 if missing

Errors return `{"detail": "..."}` with 400 or 404.

## Database migrations

Flyway applies `src/main/resources/db/migration/*.sql` on startup. Never edit an applied migration; add a new `V<n>__description.sql` instead.

## Tests

```bash
./mvnw test
```

CI (`.github/workflows/backend.yml`) also starts the app against a real PostGIS database and smoke-tests the endpoints, including a radius query.

## Structure

```text
backend/
├── pom.xml
├── mvnw
└── src/
    ├── main/java/com/tymeddos/
    │   ├── TymedDosApplication.java
    │   ├── ApiInfoController.java
    │   ├── config/        # CORS
    │   ├── exception/     # error responses
    │   └── photo/         # controller → service → repository (JDBC + PostGIS SQL)
    ├── main/resources/
    │   ├── application.yml
    │   └── db/migration/
    └── test/
```
