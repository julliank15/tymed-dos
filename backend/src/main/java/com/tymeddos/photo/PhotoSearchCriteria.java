package com.tymeddos.photo;

import java.time.OffsetDateTime;

public record PhotoSearchCriteria(
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        Double latitude,
        Double longitude,
        Double radiusKm,
        int limit,
        int offset
) {
    public boolean hasLocationFilter() {
        return latitude != null && longitude != null && radiusKm != null;
    }
}
