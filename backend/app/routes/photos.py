from fastapi import APIRouter, HTTPException, Query
from typing import Optional, List
from app.schemas.photo import Photo, PhotoCreate

router = APIRouter()

# Mock data for demonstration
mock_photos: List[Photo] = []

@router.get("/", response_model=List[Photo])
async def get_photos(
    start_date: Optional[str] = Query(None, description="Start date filter (ISO format)"),
    end_date: Optional[str] = Query(None, description="End date filter (ISO format)"),
    lat: Optional[float] = Query(None, description="Latitude for location filter"),
    lng: Optional[float] = Query(None, description="Longitude for location filter"),
    radius: Optional[float] = Query(None, description="Radius in km for location filter"),
    limit: int = Query(100, description="Maximum number of results"),
    offset: int = Query(0, description="Offset for pagination")
):
    """
    Get photos with optional filters:
    - Date range (start_date, end_date)
    - Location (lat, lng, radius)
    - Pagination (limit, offset)
    """
    # TODO: Implement actual database queries with PostGIS
    return mock_photos[offset:offset + limit]

@router.get("/{photo_id}", response_model=Photo)
async def get_photo(photo_id: str):
    """
    Get a specific photo by ID
    """
    # TODO: Implement actual database query
    photo = next((p for p in mock_photos if p.id == photo_id), None)
    if not photo:
        raise HTTPException(status_code=404, detail="Photo not found")
    return photo

@router.post("/", response_model=Photo)
async def create_photo(photo: PhotoCreate):
    """
    Create a new photo entry (for scraper/admin use)
    """
    # TODO: Implement actual database insert
    new_photo = Photo(id=f"photo_{len(mock_photos) + 1}", **photo.dict())
    mock_photos.append(new_photo)
    return new_photo
