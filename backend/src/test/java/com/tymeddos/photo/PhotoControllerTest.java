package com.tymeddos.photo;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tymeddos.exception.ApiExceptionHandler;
import com.tymeddos.exception.NotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PhotoController.class)
@Import(ApiExceptionHandler.class)
class PhotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhotoService photoService;

    @Test
    void listPhotosPreservesContract() throws Exception {
        PhotoResponse photo = samplePhoto();
        when(photoService.getPhotos(eq("2024-01-01"), eq("2024-12-31"), eq(40.0), eq(-74.0), eq(5.0), eq(25), eq(10)))
                .thenReturn(List.of(photo));

        mockMvc.perform(get("/api/photos/")
                        .queryParam("start_date", "2024-01-01")
                        .queryParam("end_date", "2024-12-31")
                        .queryParam("lat", "40.0")
                        .queryParam("lng", "-74.0")
                        .queryParam("radius", "5")
                        .queryParam("limit", "25")
                        .queryParam("offset", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("photo_1"))
                .andExpect(jsonPath("$[0].thumbnail_url").value("https://example.com/thumb.jpg"))
                .andExpect(jsonPath("$[0].location.latitude").value(40.7128))
                .andExpect(jsonPath("$[0].metadata.source").value("library"))
                .andExpect(jsonPath("$[0].metadata.tags[0]").value("history"));
    }

    @Test
    void getPhotoReturns404WhenMissing() throws Exception {
        when(photoService.getPhoto("missing")).thenThrow(new NotFoundException("Photo not found"));

        mockMvc.perform(get("/api/photos/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Photo not found"));
    }

    private PhotoResponse samplePhoto() {
        return new PhotoResponse(
                "photo_1",
                "Historic Street",
                "A downtown scene",
                "https://example.com/photo.jpg",
                "https://example.com/thumb.jpg",
                new PhotoResponse.LocationResponse(40.7128, -74.0060, "New York, NY"),
                OffsetDateTime.parse("2024-01-01T00:00:00Z"),
                new PhotoResponse.PhotoMetadataResponse("library", "Jane Doe", List.of("history", "city"))
        );
    }
}
