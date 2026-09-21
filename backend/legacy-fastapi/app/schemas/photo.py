from pydantic import BaseModel
from typing import Optional
from datetime import datetime

class Location(BaseModel):
    latitude: float
    longitude: float
    address: Optional[str] = None

class PhotoMetadata(BaseModel):
    source: Optional[str] = None
    photographer: Optional[str] = None
    tags: Optional[list[str]] = None

class PhotoBase(BaseModel):
    title: str
    description: Optional[str] = None
    url: str
    thumbnail_url: Optional[str] = None
    location: Location
    date: datetime
    metadata: Optional[PhotoMetadata] = None

class PhotoCreate(PhotoBase):
    pass

class Photo(PhotoBase):
    id: str

    class Config:
        from_attributes = True
