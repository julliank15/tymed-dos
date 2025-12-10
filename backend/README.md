# Tymed-Dos Backend

FastAPI backend for the Tymed-Dos historical photo archive system.

## Features

- RESTful API for photo and location queries
- PostgreSQL with PostGIS for geospatial data
- Pydantic models for data validation
- OpenAPI documentation
- CORS support for frontend integration

## Setup

1. Create a virtual environment:
```bash
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

2. Install dependencies:
```bash
pip install -r requirements.txt
```

3. Set up environment variables:
```bash
cp .env.example .env
# Edit .env with your database credentials
```

4. Run the development server:
```bash
uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

The API will be available at:
- Main API: http://localhost:8000
- Interactive docs: http://localhost:8000/docs
- Alternative docs: http://localhost:8000/redoc

## API Endpoints

### Photos
- `GET /api/photos/` - List photos with filters (date, location, pagination)
- `GET /api/photos/{photo_id}` - Get specific photo
- `POST /api/photos/` - Create new photo entry

### Locations
- `GET /api/locations/search` - Search locations by name
- `GET /api/locations/nearby` - Find nearby locations

## Database Setup

### Using Docker (Recommended)

```bash
docker run --name tymed-dos-db \
  -e POSTGRES_USER=tymed \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_DB=tymed_dos \
  -p 5432:5432 \
  -d postgis/postgis:15-3.3
```

### Manual Setup

1. Install PostgreSQL with PostGIS extension
2. Create database:
```sql
CREATE DATABASE tymed_dos;
\c tymed_dos
CREATE EXTENSION postgis;
```

## Project Structure

```
backend/
├── app/
│   ├── __init__.py
│   ├── main.py           # FastAPI application
│   ├── core/             # Core configuration
│   ├── models/           # SQLAlchemy models
│   ├── schemas/          # Pydantic schemas
│   └── routes/           # API endpoints
│       ├── photos.py
│       └── locations.py
├── requirements.txt
├── .env.example
└── README.md
```

## Next Steps

1. Implement database models with SQLAlchemy and GeoAlchemy2
2. Add database migrations with Alembic
3. Implement authentication/authorization
4. Add cloud storage integration (GCS or S3)
5. Implement caching with Redis
6. Add comprehensive tests
