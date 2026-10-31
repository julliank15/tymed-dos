package com.tymeddos.photo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class PhotoServiceTest {

    private PhotoRepository repository;
    private PhotoService service;

    @BeforeEach
    void setUp() {
        repository = mock(PhotoRepository.class);
        when(repository.findAll(any())).thenReturn(List.of());
        service = new PhotoService(repository);
    }

    @Test
    void dateOnlyValuesCoverWholeDays() {
        service.getPhotos("1920-01-01", "1929-12-31", null, null, null, 100, 0);

        ArgumentCaptor<PhotoSearchCriteria> captor = ArgumentCaptor.forClass(PhotoSearchCriteria.class);
        verify(repository).findAll(captor.capture());
        assertThat(captor.getValue().startDate()).isEqualTo(OffsetDateTime.parse("1920-01-01T00:00:00Z"));
        assertThat(captor.getValue().endDate().toLocalDate().toString()).isEqualTo("1929-12-31");
        assertThat(captor.getValue().endDate().getHour()).isEqualTo(23);
    }

    @Test
    void invalidDateIsRejected() {
        assertThatThrownBy(() -> service.getPhotos("not-a-date", null, null, null, null, 100, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not-a-date");
    }

    @Test
    void partialLocationFilterIsRejected() {
        assertThatThrownBy(() -> service.getPhotos(null, null, 42.33, null, null, 100, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("together");
    }

    @Test
    void outOfRangeCoordinatesAreRejected() {
        assertThatThrownBy(() -> service.getPhotos(null, null, 95.0, -83.0, 5.0, 100, 0))
                .hasMessageContaining("lat");
        assertThatThrownBy(() -> service.getPhotos(null, null, 42.0, -200.0, 5.0, 100, 0))
                .hasMessageContaining("lng");
        assertThatThrownBy(() -> service.getPhotos(null, null, 42.0, -83.0, 0.0, 100, 0))
                .hasMessageContaining("radius");
    }

    @Test
    void fullLocationFilterIsPassedThrough() {
        service.getPhotos(null, null, 42.33, -83.05, 10.0, 50, 0);

        ArgumentCaptor<PhotoSearchCriteria> captor = ArgumentCaptor.forClass(PhotoSearchCriteria.class);
        verify(repository).findAll(captor.capture());
        assertThat(captor.getValue().hasLocationFilter()).isTrue();
        assertThat(captor.getValue().radiusKm()).isEqualTo(10.0);
    }
}
