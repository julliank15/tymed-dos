package com.tymeddos.photo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhotoResponse(
        String id,
        String title,
        String description,
        String url,
        @JsonProperty("thumbnail_url") String thumbnailUrl,
        LocationResponse location,
        OffsetDateTime date,
        PhotoMetadataResponse metadata
) {
    public record LocationResponse(
            double latitude,
            double longitude,
            String address
    ) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record PhotoMetadataResponse(
            String source,
            String photographer,
            List<String> tags
    ) {}
}
