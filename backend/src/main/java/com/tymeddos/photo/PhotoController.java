package com.tymeddos.photo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/photos")
@Validated
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    // Matches both /api/photos and /api/photos/ (Spring Boot 3 no longer treats them as the same).
    @GetMapping({"", "/"})
    public List<PhotoResponse> getPhotos(
            @RequestParam(name = "start_date", required = false) String startDate,
            @RequestParam(name = "end_date", required = false) String endDate,
            @RequestParam(name = "lat", required = false) Double latitude,
            @RequestParam(name = "lng", required = false) Double longitude,
            @RequestParam(name = "radius", required = false) Double radiusKm,
            @RequestParam(name = "limit", defaultValue = "100") @Min(1) @Max(500) int limit,
            @RequestParam(name = "offset", defaultValue = "0") @Min(0) int offset
    ) {
        return photoService.getPhotos(startDate, endDate, latitude, longitude, radiusKm, limit, offset);
    }

    @GetMapping("/{photoId}")
    public PhotoResponse getPhoto(@PathVariable String photoId) {
        return photoService.getPhoto(photoId);
    }
}
