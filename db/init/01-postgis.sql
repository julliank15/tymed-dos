-- Runs once, the first time the database volume is created.
-- The postgis/postgis image usually enables PostGIS already; this makes it explicit.
CREATE EXTENSION IF NOT EXISTS postgis;
