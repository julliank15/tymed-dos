from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.routes import photos, locations

app = FastAPI(
    title="Tymed-Dos API",
    description="Historical Photo Archive API",
    version="0.1.0"
)

# CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173", "http://localhost:3000"],  # Vite default port
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Include routers
app.include_router(photos.router, prefix="/api/photos", tags=["photos"])
app.include_router(locations.router, prefix="/api/locations", tags=["locations"])

@app.get("/")
async def root():
    return {
        "message": "Welcome to Tymed-Dos API",
        "docs": "/docs",
        "version": "0.1.0"
    }

@app.get("/health")
async def health_check():
    return {"status": "healthy"}
