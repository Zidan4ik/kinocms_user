package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Hall;
import com.example.kinocms_user.repository.HallRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HallServiceImpTest {
    @Mock
    private HallRepository hallRepository;
    @InjectMocks
    private HallServiceImp hallService;
    private List<Hall> loadedHalls;

    @BeforeEach
    void setUp() {
        loadedHalls = loadHalls();
    }

    @Test
    void testGetAll_ReturnsAllHalls() {
        Mockito.when(hallRepository.findAll()).thenReturn(loadedHalls);
        List<Hall> halls = hallService.getAll();
        assertNotNull(halls, "Collection halls shouldn't null");
        assertEquals(loadedHalls.size(), halls.size(), "Sizes list of shares should be match");
    }

    @Test
    void testGetById_ReturnHall_WhenHallIsPresent() {
        Long expectedId = 1L;
        Hall expectedHall = loadedHalls.stream()
                .filter(h -> h.getId().equals(expectedId))
                .findFirst()
                .orElse(null);
        Mockito.when(hallRepository.findById(1L)).thenReturn(Optional.ofNullable(expectedHall));
        Optional<Hall> hallById = hallService.getById(expectedId);
        assertTrue(hallById.isPresent(), "Hall should be found in database");
        assertNotNull(expectedHall, "Hall should be present");
        assertEquals(expectedHall.getId(), hallById.get().getId(), "Hall id should match");
    }

    @Test
    void testGetAllByCinema_ReturnHallsForGivenCinema_WhenHallIsPresent() {
        Cinema expectedCinema = new Cinema();
        expectedCinema.setId(2L);
        List<Hall> expectedHalls = loadedHalls.stream()
                .filter(h -> h.getCinema().getId().equals(expectedCinema.getId()))
                .toList();
        Mockito.when(hallRepository.getAllByCinema(expectedCinema)).thenReturn(expectedHalls);
        List<Hall> halls = hallService.getAllByCinema(expectedCinema);
        assertNotNull(halls, "Collection should not null");
        assertEquals(expectedHalls.size(), halls.size(), "Sizes list of shares should be match");
    }

    private List<Hall> loadHalls() {
        Cinema cinema1 = new Cinema();
        cinema1.setId(1L);
        Cinema cinema2 = new Cinema();
        cinema2.setId(2L);

        Hall hall1 = new Hall(1L, 1, "Schema 1", "Banner 1", "http://example.com/ceo1", LocalDate.now(), cinema1, null, null, null);
        Hall hall2 = new Hall(2L, 2, "Schema 2", "Banner 2", "http://example.com/ceo2", LocalDate.now(), cinema1, null, null, null);
        Hall hall3 = new Hall(3L, 3, "Schema 3", "Banner 3", "http://example.com/ceo3", LocalDate.now(), cinema2, null, null, null);
        return List.of(hall1, hall2, hall3);
    }
}