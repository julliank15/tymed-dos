# Tymed-Dos

A historical photo archival and visualization system that web scrapes historical photos, organizes them by location, and displays them on an interactive timeline with map integration.

## 🎯 Project Overview

Tymed-Dos is a full-stack application designed to:
- Acquire historical photos from various online archives
- Organize and store photos with geospatial metadata
- Provide an interactive visualization interface with timeline and map views
- Enable location-based and date-based photo exploration

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
│   (FastAPI)         │      │  (GCS/S3)           │
│   PostgreSQL +      │      └─────────────────────┘
│   PostGIS           │
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
- **React** with **TypeScript** - Component-based UI
- **Vite** - Fast development server and build tool
- **Tailwind CSS** - Utility-first CSS framework
- **Mapbox GL JS** (via react-map-gl) - Interactive map visualization
- **Custom Timeline Component** - Chronological photo browsing

### Backend
- **FastAPI** - Modern, high-performance Python web framework
- **PostgreSQL** with **PostGIS** - Database with spatial extensions
- **SQLAlchemy** - ORM for database operations
- **Pydantic** - Data validation and settings management

### Data Acquisition
- **Scrapy** - Web scraping framework
- **GeoPy** - Geocoding and location processing
- **Pandas** - Data manipulation and analysis

## 🚀 Getting Started

### Prerequisites

- **Node.js** 18+ and npm
- **Python** 3.10+
- **Docker** (runs PostgreSQL 16 + PostGIS 3.4 via Docker Compose), or PostgreSQL 16 with PostGIS installed locally
- **Mapbox Account** (for map visualization)

### Quick Start

#### 1. Frontend Setup

```bash
cd frontend
npm install

# Create environment file
cp .env.example .env
# Add your Mapbox token to .env

# Start development server
npm run dev
```

The frontend will be available at http://localhost:5173

#### 2. Backend Setup

```bash
cd backend

# Create virtual environment
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate

# Install dependencies
pip install -r requirements.txt

# Create environment file
cp .env.example .env
# Configure your database URL in .env

# Start development server
uvicorn app.main:app --reload
```

The API will be available at http://localhost:8000
- API Docs: http://localhost:8000/docs

#### 3. Scraper Setup

```bash
cd scraper

# Create virtual environment
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate

# Install dependencies
pip install -r requirements.txt

# Run example spider
scrapy crawl example -o output.json
```

### Database Setup

#### Using Docker (Recommended)

Runs PostgreSQL 16 + PostGIS 3.4 from the repo root:

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

#### Manual Setup

1. Install PostgreSQL with PostGIS
2. Create database:
```sql
CREATE DATABASE tymed_dos;
\c tymed_dos
CREATE EXTENSION postgis;
```

## 📁 Project Structure

```
tymed-dos/
├── frontend/                 # React + Vite frontend
│   ├── src/
│   │   ├── components/      # Reusable components
│   │   │   ├── Timeline.tsx
│   │   │   └── Map.tsx
│   │   ├── pages/           # Page components
│   │   ├── types/           # TypeScript type definitions
│   │   └── utils/           # Utility functions
│   ├── package.json
│   └── vite.config.ts
│
├── backend/                  # FastAPI backend
│   ├── app/
│   │   ├── main.py          # FastAPI application
│   │   ├── routes/          # API endpoints
│   │   ├── models/          # Database models
│   │   ├── schemas/         # Pydantic schemas
│   │   └── core/            # Core configuration
│   └── requirements.txt
│
├── scraper/                  # Scrapy web scraper
│   ├── tymed_scraper/
│   │   ├── spiders/         # Spider implementations
│   │   ├── pipelines/       # Data processing pipelines
│   │   ├── items.py         # Item definitions
│   │   └── settings.py      # Scrapy settings
│   └── requirements.txt
│
└── README.md
```

## 🎨 Features

### Current Features
- ✅ React frontend with Vite
- ✅ Tailwind CSS styling
- ✅ Basic Timeline component
- ✅ Map component with Mapbox GL JS integration
- ✅ FastAPI backend with REST endpoints
- ✅ Scrapy spider framework
- ✅ Geocoding pipeline with GeoPy
- ✅ Data validation pipeline

### Planned Features
- 🔲 PostgreSQL/PostGIS integration
- 🔲 Photo upload and storage
- 🔲 Advanced filtering and search
- 🔲 User authentication
- 🔲 Photo collections and favorites
- 🔲 Social sharing features
- 🔲 Image optimization pipeline
- 🔲 Multiple scraper implementations

## 🧪 Development

### Frontend Development

```bash
cd frontend
npm run dev          # Start dev server
npm run build        # Build for production
npm run preview      # Preview production build
npm run lint         # Lint code
```

### Backend Development

```bash
cd backend
uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

### Scraper Development

```bash
cd scraper
scrapy crawl <spider_name> -o output.json
```

## 📝 API Documentation

Once the backend is running, visit:
- **Interactive API Docs**: http://localhost:8000/docs
- **Alternative Docs**: http://localhost:8000/redoc

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is open source and available under the MIT License.

## 🔗 Resources

- [FastAPI Documentation](https://fastapi.tiangolo.com/)
- [React Documentation](https://react.dev/)
- [Scrapy Documentation](https://docs.scrapy.org/)
- [PostGIS Documentation](https://postgis.net/documentation/)
- [Mapbox GL JS Documentation](https://docs.mapbox.com/mapbox-gl-js/)
- [Vite Documentation](https://vitejs.dev/)
- [Tailwind CSS Documentation](https://tailwindcss.com/)
