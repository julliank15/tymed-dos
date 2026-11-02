package com.tymeddos.photo;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PhotoRepository {

    private static final String SELECT_COLUMNS = """
            SELECT id, title, description, url, thumbnail_url,
                   ST_Y(location::geometry) AS latitude, ST_X(location::geometry) AS longitude,
                   address, date_taken, source, photographer, tags
            FROM photos
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<PhotoResponse> rowMapper = this::mapRow;

    public PhotoRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PhotoResponse> findAll(PhotoSearchCriteria criteria) {
        // Only add conditions for filters that are present. A pattern like
        // "(:startDate IS NULL OR date >= :startDate)" fails on PostgreSQL when the value is null
        // ("could not determine data type of parameter").
        List<String> conditions = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", criteria.limit())
                .addValue("offset", criteria.offset());

        if (criteria.startDate() != null) {
            conditions.add("date_taken >= :startDate");
            params.addValue("startDate", criteria.startDate());
        }
        if (criteria.endDate() != null) {
            conditions.add("date_taken <= :endDate");
            params.addValue("endDate", criteria.endDate());
        }
        if (criteria.hasLocationFilter()) {
            // Uses the GIST index idx_photos_location.
            conditions.add("ST_DWithin("
                    + "location, "
                    + "ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography, "
                    + ":radiusMeters)");
            params.addValue("longitude", criteria.longitude())
                    .addValue("latitude", criteria.latitude())
                    .addValue("radiusMeters", criteria.radiusKm() * 1000.0d);
        }

        StringBuilder sql = new StringBuilder(SELECT_COLUMNS);
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        sql.append(" ORDER BY date_taken DESC, id ASC LIMIT :limit OFFSET :offset");
        return jdbcTemplate.query(sql.toString(), params, rowMapper);
    }

    public Optional<PhotoResponse> findById(String photoId) {
        UUID id;
        try {
            id = UUID.fromString(photoId);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
        String sql = SELECT_COLUMNS + " WHERE id = :photoId";
        List<PhotoResponse> results = jdbcTemplate.query(
                sql,
                new MapSqlParameterSource("photoId", id),
                rowMapper
        );
        return results.stream().findFirst();
    }

    private PhotoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PhotoResponse(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("url"),
                rs.getString("thumbnail_url"),
                new PhotoResponse.LocationResponse(
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("address")
                ),
                rs.getObject("date_taken", OffsetDateTime.class),
                new PhotoResponse.PhotoMetadataResponse(
                        rs.getString("source"),
                        rs.getString("photographer"),
                        toTagList(rs.getArray("tags"))
                )
        );
    }

    private List<String> toTagList(Array tagsArray) throws SQLException {
        if (tagsArray == null) {
            return null;
        }

        Object array = tagsArray.getArray();
        if (array instanceof String[] stringArray) {
            return Arrays.asList(stringArray);
        }

        return null;
    }
}
