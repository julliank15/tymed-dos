-- Evolves the photos table created in V1 (kept unchanged so applied checksums stay valid):
--   * UUID primary key
--   * PostGIS geography(Point,4326) location instead of separate latitude/longitude
--   * date_taken + date_precision to support uncertain historical dates
--   * source_url + UNIQUE (source, source_url) so scraper ingests are idempotent
--   * created_at / updated_at audit columns

-- id: VARCHAR -> UUID (fails loudly if any existing id is not a valid UUID)
ALTER TABLE photos ALTER COLUMN id TYPE uuid USING id::uuid;
ALTER TABLE photos ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- location: lat/lon -> geography(Point,4326)
ALTER TABLE photos ADD COLUMN location geography(Point, 4326);
UPDATE photos SET location = ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography;
ALTER TABLE photos ALTER COLUMN location SET NOT NULL;

DROP INDEX IF EXISTS idx_photos_geography;
ALTER TABLE photos DROP COLUMN latitude;
ALTER TABLE photos DROP COLUMN longitude;
CREATE INDEX idx_photos_location ON photos USING GIST (location);

-- date -> date_taken, plus precision (the stored timestamp is the start of the known period)
ALTER TABLE photos RENAME COLUMN date TO date_taken;
ALTER TABLE photos ADD COLUMN date_precision VARCHAR(10) NOT NULL DEFAULT 'exact';
ALTER TABLE photos ADD CONSTRAINT chk_photos_date_precision
    CHECK (date_precision IN ('exact', 'year', 'decade'));
ALTER INDEX idx_photos_date RENAME TO idx_photos_date_taken;

-- idempotent scraper ingest. Rows with NULL source or source_url are not constrained (NULLs are distinct).
ALTER TABLE photos ADD COLUMN source_url TEXT;
UPDATE photos SET source_url = url WHERE source IS NOT NULL;
ALTER TABLE photos ADD CONSTRAINT uq_photos_source_source_url UNIQUE (source, source_url);

-- audit columns
ALTER TABLE photos ADD COLUMN created_at TIMESTAMPTZ NOT NULL DEFAULT now();
ALTER TABLE photos ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT now();

CREATE FUNCTION set_photos_updated_at() RETURNS trigger AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_photos_updated_at
    BEFORE UPDATE ON photos
    FOR EACH ROW EXECUTE FUNCTION set_photos_updated_at();
