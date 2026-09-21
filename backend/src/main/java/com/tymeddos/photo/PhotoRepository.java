package com.tymeddos.photo;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PhotoRepository {

    private static final String SELECT_COLUMNS = """
            SELECT id, title, description, url, thumbnail_url, latitude, longitude, address, date, source, photographer, tags
            FROM photos
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<PhotoResponse> rowMapper = this::mapRow;

    public PhotoRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PhotoResponse> findAll(PhotoSearchCriteria criteria) {
        StringBuilder sql = new StringBuilder(SELECT_COLUMNS)
                .append(" WHERE (:startDate IS NULL OR date >= :startDate)")
                .append(" AND (:endDate IS NULL OR date <= :endDate)");

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("startDate", criteria.startDate())
                .addValue("endDate", criteria.endDate())
                .addValue("limit", criteria.limit())
                .addValue("offset", criteria.offset());

        if (criteria.hasLocationFilter()) {
            sql.append(" AND ST_DWithin(")
                    .append("ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,")
                    .append("ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,")
                    .append(":radiusMeters)");
            params.addValue("longitude", criteria.longitude())
                    .addValue("latitude", criteria.latitude())
                    .addValue("radiusMeters", criteria.radiusKm() * 1000.0d);
        }

        sql.append(" ORDER BY date DESC, id ASC LIMIT :limit OFFSET :offset");
        return jdbcTemplate.query(sql.toString(), params, rowMapper);
    }

    public Optional<PhotoResponse> findById(String photoId) {
        String sql = SELECT_COLUMNS + " WHERE id = :photoId";
        List<PhotoResponse> results = jdbcTemplate.query(
                sql,
                new MapSqlParameterSource("photoId", photoId),
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
                rs.getObject("date", OffsetDateTime.class),
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
