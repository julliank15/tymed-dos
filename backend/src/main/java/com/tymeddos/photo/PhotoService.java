package com.tymeddos.photo;

import com.tymeddos.exception.NotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<PhotoResponse> getPhotos(
            String startDate,
            String endDate,
            Double latitude,
            Double longitude,
            Double radiusKm,
            int limit,
            int offset
    ) {
        PhotoSearchCriteria criteria = new PhotoSearchCriteria(
                parseDate(startDate, false),
                parseDate(endDate, true),
                latitude,
                longitude,
                radiusKm,
                limit,
                offset
        );
        return photoRepository.findAll(criteria);
    }

    public PhotoResponse getPhoto(String photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo not found"));
    }

    private OffsetDateTime parseDate(String value, boolean endOfDay) {
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return OffsetDateTime.parse(value);
        } catch (DateTimeParseException ignored) {
            try {
                LocalDate localDate = LocalDate.parse(value);
                LocalTime time = endOfDay ? LocalTime.MAX : LocalTime.MIDNIGHT;
                return localDate.atTime(time).atOffset(ZoneOffset.UTC);
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Invalid ISO date value: " + value);
            }
        }
    }
}
