# Tymed-Dos Backend

Spring Boot backend for the Tymed-Dos historical photo archive system.

## MVP Scope

This backend migration currently implements the Spring Boot MVP:

- `GET /api/photos/`
- `GET /api/photos/{photo_id}`
- `GET /`
- `GET /health`
- PostgreSQL/PostGIS connectivity
- Flyway baseline migration
- OpenAPI docs at `/docs`

Deferred from this MVP:

- `POST /api/photos/`
- `GET /api/locations/search`
- `GET /api/locations/nearby`
- Docker/CI polish
- Full API compatibility audit

## Requirements

- Java 17+
- Maven 3.9+
- PostgreSQL 15+ with PostGIS

## Setup

1. Start PostgreSQL/PostGIS from the repository root:
```bash
cd /home/runner/work/tymed-dos/tymed-dos
docker compose up -d postgres
```

2. Run the backend:
```bash
cd /home/runner/work/tymed-dos/tymed-dos/backend
./mvnw spring-boot:run
```

If `mvnw` is unavailable, use your installed Maven:
```bash
cd /home/runner/work/tymed-dos/tymed-dos/backend
mvn spring-boot:run
```

3. API endpoints:
- Main API: http://localhost:8080
- OpenAPI docs: http://localhost:8080/docs
- OpenAPI JSON: http://localhost:8080/openapi

## Configuration

The backend uses these environment variables:

- `SPRING_DATASOURCE_URL` (default: `jdbc:postgresql://localhost:5432/tymed_dos`)
- `SPRING_DATASOURCE_USERNAME` (default: `tymed`)
- `SPRING_DATASOURCE_PASSWORD` (default: `password`)

## Database

Flyway automatically applies `/src/main/resources/db/migration/V1__create_photos_table.sql` on startup.

The migration creates a `photos` table with:
- photo metadata columns
- latitude/longitude storage
- a PostGIS-backed spatial index for nearby filtering

## Testing

Run backend tests with:
```bash
cd /home/runner/work/tymed-dos/tymed-dos/backend
mvn test
```

## Project Structure

```text
backend/
├── legacy-fastapi/              # Deprecated FastAPI scaffold kept for reference
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/tymeddos/
│   │   │   ├── TymedDosApplication.java
│   │   │   ├── ApiInfoController.java
│   │   │   ├── config/
│   │   │   ├── exception/
│   │   │   └── photo/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   └── test/
└── README.md
```
