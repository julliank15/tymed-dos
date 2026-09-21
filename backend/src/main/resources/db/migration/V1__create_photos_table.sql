CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS photos (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    url TEXT NOT NULL,
    thumbnail_url TEXT,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    address TEXT,
    date TIMESTAMPTZ NOT NULL,
    source VARCHAR(255),
    photographer VARCHAR(255),
    tags TEXT[]
);

CREATE INDEX IF NOT EXISTS idx_photos_date ON photos (date DESC);
CREATE INDEX IF NOT EXISTS idx_photos_geography ON photos USING GIST ((ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography));
