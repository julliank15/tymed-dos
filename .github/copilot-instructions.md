# Copilot Instructions for Tymed-Dos

## Project Overview

Tymed-Dos is a historical photo archival and visualization system that web scrapes historical photos, organizes them by location on the backend, and displays them on an interactive timeline with map integration on the frontend.

## Technology Stack

This project uses a modern, scalable technology stack designed for data acquisition, processing, storage, and visualization of historical photos with geospatial context.

### 1. Data Acquisition & Processing (The Scraper Engine)

**Core Technologies:**
- **Python (Scrapy)**: Primary web scraping framework for acquiring historical photos from various sources
  - Used for building robust, scalable web crawlers
  - Handles concurrent requests, data extraction, and error handling
  - Supports multiple output formats and data pipelines

- **Python (GeoPy, GDAL/Fiona)**: Geospatial processing libraries
  - **GeoPy**: Geocoding and reverse geocoding for location data
  - **GDAL/Fiona**: Reading, writing, and transforming geospatial data formats
  - Used to normalize and validate location information from scraped data

- **Python (Pandas)**: Data manipulation and analysis
  - Clean and transform scraped photo metadata
  - Handle data aggregation and preprocessing
  - Prepare data for backend ingestion

**Key Responsibilities:**
- Web scraping historical photo archives
- Extracting metadata (dates, locations, descriptions)
- Geocoding location information
- Data validation and cleaning
- Preparing structured data for storage

### 2. Backend Services & Storage (The Organizer)

**Core Technologies:**
- **Python (FastAPI)**: Modern, high-performance web framework
  - RESTful API for frontend communication
  - Async/await support for efficient request handling
  - Automatic OpenAPI documentation
  - Type hints and validation with Pydantic

- **PostgreSQL with PostGIS**: Primary database with spatial extensions
  - **PostgreSQL**: Robust relational database for storing photo metadata
  - **PostGIS**: Spatial database extension for geographic queries
  - Enables efficient location-based queries (e.g., photos within radius, by region)
  - Supports spatial indexing for performance

- **Cloud Storage (Google Cloud Storage or AWS S3)**: Object storage for images
  - Stores actual photo files (original and optimized versions)
  - CDN integration for fast content delivery
  - Scalable and cost-effective storage solution
  - Supports different storage classes for archival vs. active data

**Key Responsibilities:**
- API endpoints for querying photos by location, date, and other criteria
- Managing photo metadata in PostgreSQL/PostGIS
- Handling image uploads and retrieval from cloud storage
- Geospatial queries for map-based filtering
- Data organization and indexing

### 3. Frontend Presentation (The React UI)

**Core Technologies:**
- **React & Tailwind CSS**: Modern UI framework and utility-first CSS
  - **React**: Component-based UI architecture
  - **Tailwind CSS**: Rapid, responsive styling with utility classes
  - Enables maintainable and reusable component design

- **Mapbox GL JS (via react-map-gl)**: Interactive map visualization
  - High-performance vector map rendering
  - Custom markers and layers for photo locations
  - Interactive pan, zoom, and clustering
  - Integrates seamlessly with React

- **Custom React Component - Timeline**: Reusable timeline component
  - Displays photos chronologically
  - Interactive date selection and filtering
  - Integrates with map component for location-based viewing
  - Responsive design for various screen sizes

**Key Responsibilities:**
- Interactive map displaying photo locations
- Timeline component for browsing photos by date
- Photo detail views with metadata
- Responsive, accessible user interface
- Integration between timeline and map views

## Architecture Overview

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
│  (Pandas)           │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐      ┌─────────────────────┐
│   Backend API       │◄────►│  Cloud Storage      │
│   (FastAPI)         │      │  (GCS/S3)           │
│                     │      │  (Images)           │
│   PostgreSQL +      │      └─────────────────────┘
│   PostGIS           │
│   (Metadata)        │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   Frontend UI       │
│   (React)           │
│   - Timeline        │
│   - Map (Mapbox)    │
│   (Tailwind CSS)    │
└─────────────────────┘
```

## Development Guidelines

### Code Organization
- **Backend**: Organize FastAPI routes by resource (photos, locations, etc.)
- **Frontend**: Component-based architecture with clear separation of concerns
- **Data Processing**: Modular Scrapy spiders and data pipelines

### Key Considerations
- **Performance**: Use async operations where possible (FastAPI, React)
- **Scalability**: Design for large datasets and concurrent users
- **Data Quality**: Validate and clean all scraped data before storage
- **Geospatial Accuracy**: Ensure proper coordinate reference systems (CRS)
- **Security**: Sanitize inputs, implement authentication where needed
- **Testing**: Unit tests for backend logic, integration tests for APIs

### Recommended Patterns
- Use Pydantic models for API request/response validation
- Implement pagination for large result sets
- Cache frequently accessed data
- Optimize images for web delivery (multiple sizes/formats)
- Use spatial indexes for location queries
- Implement proper error handling and logging throughout

## Getting Started

When working on this project:
1. **Backend**: Start with FastAPI routes and PostgreSQL schema
2. **Scraper**: Build Scrapy spiders targeting specific photo archives
3. **Frontend**: Create the base React app with Tailwind CSS
4. **Integration**: Connect timeline and map components with backend API
5. **Testing**: Validate end-to-end flow from scraping to display

## Additional Resources
- FastAPI Documentation: https://fastapi.tiangolo.com/
- Scrapy Documentation: https://docs.scrapy.org/
- PostGIS Documentation: https://postgis.net/documentation/
- Mapbox GL JS Documentation: https://docs.mapbox.com/mapbox-gl-js/
- React Documentation: https://react.dev/
