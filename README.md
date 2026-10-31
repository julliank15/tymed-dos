# Tymed-Dos

A historical photo archival and visualization system that scrapes historical photos, stores them with geospatial metadata, and displays them through a React timeline and map UI.

## 🎯 Project Overview

Tymed-Dos is organized into three major parts:
- a **React + Vite** frontend for timeline and map visualization
- a **Spring Boot** backend for REST APIs backed by PostgreSQL/PostGIS
- a **Scrapy** scraper pipeline for ingesting and enriching historical photo data

## 🏗️ Architecture

```
┌─────────────────────┐
│   Data Sources      │
│  (Web Archives)     │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  Scraper Engine     │
│  (Scrapy + GeoPy)   │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐      ┌─────────────────────┐
│   Backend API       │◄────►│  Cloud Storage      │
│  (Spring Boot)      │      │  (GCS/S3)           │
│ PostgreSQL +        │      └─────────────────────┘
│ PostGIS             │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   Frontend UI       │
│   (React + Vite)    │
│   - Timeline        │
│   - Map (Mapbox)    │
└─────────────────────┘
```

## 🛠️ Technology Stack

### Frontend
- **React** with **TypeScript**
- **Vite**
- **Tailwind CSS**
- **Mapbox GL JS** via `react-map-gl`

### Backend
- **Java 21** (Amazon Corretto 21 recommended)
- **Spring Boot**
- **Spring Web / JDBC / Validation**
- **Flyway**
- **PostgreSQL** with **PostGIS**
- **Springdoc OpenAPI**

### Data Acquisition
- **Scrapy**
- **GeoPy**
- **Pandas**

## 🚀 Getting Started

### Prerequisites

- **Node.js** 18+ and npm
- **Java 21** (Amazon Corretto 21 recommended). Maven is not needed; the backend ships with `./mvnw`
- **Python** 3.10+
- **Docker** (runs PostgreSQL 16 + PostGIS 3.4 via Docker Compose)
- **Mapbox Account** (for map visualization)

### 1. Frontend Setup

```bash
cd frontend
npm install
cp .env.example .env
npm run dev
```

The frontend will be available at http://localhost:5173

### 2. Database Setup

From the repository root:

```bash
cp .env.example .env    # then set POSTGRES_PASSWORD
docker compose up -d
docker compose ps       # wait for "healthy"

# Check PostGIS is enabled
docker compose exec postgres sh -c 'psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "SELECT postgis_full_version();"'
```

Stop with `docker compose down` (add `-v` to delete the data volume and start fresh).

> **Upgrading from the old PostgreSQL 15 setup?** The existing data volume won't start on 16
> ("database files are incompatible with server"). There's no real data yet, so reset it:
> `docker compose down -v && docker compose up -d`.

### 3. Backend Setup

```bash
cd backend
./mvnw spring-boot:run
```

The backend reads database settings from the same root `.env`.

The backend will be available at http://localhost:8080
- Health (includes DB check): http://localhost:8080/actuator/health
- API docs: http://localhost:8080/docs
- OpenAPI JSON: http://localhost:8080/openapi

### 4. Scraper Setup

```bash
cd scraper
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
pip install -r requirements.txt
scrapy crawl example -o output.json
```

## 📁 Project Structure

```
tymed-dos/
├── frontend/
├── backend/
│   ├── pom.xml
│   ├── mvnw
│   └── src/
├── scraper/
├── db/init/                    # SQL run on first DB start (enables PostGIS)
├── .env.example                # copy to .env for local DB credentials
└── docker-compose.yml
```

## ✅ Current Backend MVP

Implemented in Spring Boot:
- `GET /`
- `GET /health`
- `GET /api/photos` (filters: `start_date`, `end_date`, `lat` + `lng` + `radius` in km, `limit`, `offset`)
- `GET /api/photos/{photo_id}`
- `GET /actuator/health`

Deferred from this migration phase:
- `POST /api/photos/`
- `GET /api/locations/search`
- `GET /api/locations/nearby`
- Full API compatibility audit and documentation

## 🧪 Development

### Frontend

```bash
cd frontend
npm run dev
npm run build
npm run lint
```

### Backend

```bash
cd backend
./mvnw test
./mvnw spring-boot:run
```

### Scraper

```bash
cd scraper
scrapy crawl <spider_name> -o output.json
```

## 🔗 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev/)
- [Scrapy Documentation](https://docs.scrapy.org/)
- [PostGIS Documentation](https://postgis.net/documentation/)
- [Mapbox GL JS Documentation](https://docs.mapbox.com/mapbox-gl-js/)
- [Vite Documentation](https://vitejs.dev/)
