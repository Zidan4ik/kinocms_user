package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.repository.CinemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CinemaServiceImpTest {
    @Mock
    private CinemaRepository cinemaRepository;
    @InjectMocks
    private CinemaServiceImp cinemaService;
    private List<Cinema> loadedCinemas;

    @BeforeEach
    void setUp() {
        loadedCinemas = loadCinemas();
    }

    @Test
    void getAll() {
        Mockito.when(cinemaRepository.findAll()).thenReturn(loadedCinemas);
        List<Cinema> cinemas = cinemaService.getAll();
        assertNotNull(cinemas, "Collection should be not null");
        assertEquals(loadedCinemas.size(), cinemas.size(), "Sizes list of shares should be match");
    }

    @Test
    void getById() {
        Long expectedId = 1L;
        Cinema expectedCinema = loadedCinemas.stream()
                .filter(c -> c.getId().equals(expectedId))
                .findFirst()
                .orElse(null);
        Mockito.when(cinemaRepository.findById(expectedId)).thenReturn(Optional.ofNullable(expectedCinema));
        Optional<Cinema> cinemaById = cinemaService.getById(expectedId);
        assertTrue(cinemaById.isPresent(), "User should be present");
        assertEquals(expectedId, cinemaById.get().getId(), "Id should match");
    }

    private List<Cinema> loadCinemas() {
        Cinema cinema1 = new Cinema(1L, "logo_cinema1.png", "banner_cinema1.png",
                "https://example.com/ceo/cinema1", LocalDate.of(2020, 5, 20),
                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()
        );

        Cinema cinema2 = new Cinema(2L, "logo_cinema2.png", "banner_cinema2.png",
                "https://example.com/ceo/cinema2", LocalDate.of(2018, 10, 15),
                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()
        );

        Cinema cinema3 = new Cinema(3L, "logo_cinema3.png", "banner_cinema3.png",
                "https://example.com/ceo/cinema3", LocalDate.of(2022, 1, 5),
                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()
        );
        return List.of(cinema1, cinema2, cinema3);
    }
}