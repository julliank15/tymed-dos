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
- **Java 17**
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
- **Java** 17+
- **Maven** 3.9+
- **Python** 3.10+
- **PostgreSQL** 15+ with PostGIS
- **Mapbox Account** (for map visualization)

### 1. Frontend Setup

```bash
cd /home/runner/work/tymed-dos/tymed-dos/frontend
npm install
cp .env.example .env
npm run dev
```

The frontend will be available at http://localhost:5173

### 2. Database Setup

From the repository root:

```bash
cd /home/runner/work/tymed-dos/tymed-dos
docker compose up -d postgres
```

### 3. Backend Setup

```bash
cd /home/runner/work/tymed-dos/tymed-dos/backend
./mvnw spring-boot:run
```

The backend will be available at http://localhost:8080
- API docs: http://localhost:8080/docs
- OpenAPI JSON: http://localhost:8080/openapi

### 4. Scraper Setup

```bash
cd /home/runner/work/tymed-dos/tymed-dos/scraper
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
│   ├── legacy-fastapi/         # Deprecated Python mock backend retained for reference
│   ├── pom.xml
│   ├── mvnw
│   └── src/
├── scraper/
└── docker-compose.yml
```

## ✅ Current Backend MVP

Implemented in Spring Boot:
- `GET /`
- `GET /health`
- `GET /api/photos/`
- `GET /api/photos/{photo_id}`

Deferred from this migration phase:
- `POST /api/photos/`
- `GET /api/locations/search`
- `GET /api/locations/nearby`
- Docker/CI support for the Java backend
- Full API compatibility audit and documentation

## 🧪 Development

### Frontend

```bash
cd /home/runner/work/tymed-dos/tymed-dos/frontend
npm run dev
npm run build
npm run lint
```

### Backend

```bash
cd /home/runner/work/tymed-dos/tymed-dos/backend
./mvnw test
./mvnw spring-boot:run
```

### Scraper

```bash
cd /home/runner/work/tymed-dos/tymed-dos/scraper
scrapy crawl <spider_name> -o output.json
```

## 🔗 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev/)
- [Scrapy Documentation](https://docs.scrapy.org/)
- [PostGIS Documentation](https://postgis.net/documentation/)
- [Mapbox GL JS Documentation](https://docs.mapbox.com/mapbox-gl-js/)
- [Vite Documentation](https://vitejs.dev/)
