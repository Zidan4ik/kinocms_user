package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.kinocms_user.util.TestDataUtil.loadFilms;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceImpTest {
    @Mock
    private FilmRepository filmRepository;
    @InjectMocks
    private FilmServiceImp filmService;
    private List<Film> loadedFilms;

    @BeforeEach
    void setUp() {
        loadedFilms = loadFilms();
    }

    @Test
    void testFindFilms_ReturnInactiveFilms_WhenFilmsIsPresent() {
        boolean expectedStatus = false;
        Mockito.when(filmRepository.findAll()).thenReturn(loadedFilms);
        List<Film> films = filmService.findFilmsIsActive(expectedStatus);
        assertNotNull(films, "Collection should not be null");
        assertEquals(2, films.size(), "Collection should be responding sizes");
        assertEquals(2, films.get(0).getId(), "Films id should be match");
    }

    @Test
    void testFindFilms_ReturnActiveFilms_WhenFilmsIsPresent() {
        boolean expectedStatus = true;
        Mockito.when(filmRepository.findAll()).thenReturn(loadedFilms);
        List<Film> films = filmService.findFilmsIsActive(expectedStatus);
        assertNotNull(films, "Collection should not be null");
        assertEquals(2, films.size(), "Collection should be responding sizes");
        assertEquals(1, films.get(0).getId(), "Films id should be match");
    }

    @Test
    void testFindFilms_ReturnInactiveFilms_WhenFilmsIsEmpty() {
        boolean expectedStatus = false;
        List<Film> expectedFilms = new ArrayList<>();
        Film film = new Film();
        film.setDateStart(LocalDate.now().minusDays(5));
        film.setDateEnd(LocalDate.now().minusDays(5));
        expectedFilms.add(film);
        Mockito.when(filmRepository.findAll()).thenReturn(expectedFilms);
        List<Film> films = filmService.findFilmsIsActive(expectedStatus);
        assertTrue(films.isEmpty(), "Collection should be null");
    }

    @Test
    void testFindFilms_ReturnActiveFilms_WhenFilmsIsEmpty() {
        boolean expectedStatus = true;
        List<Film> expectedFilms = new ArrayList<>();
        Film film = new Film();
        film.setDateStart(LocalDate.now().plusDays(5));
        film.setDateEnd(LocalDate.now().plusDays(5));
        expectedFilms.add(film);
        Mockito.when(filmRepository.findAll()).thenReturn(expectedFilms);
        List<Film> films = filmService.findFilmsIsActive(expectedStatus);
        assertTrue(films.isEmpty(), "Collection should be null");
    }

    @Test
    void testFindFilms_ReturnInactiveFilms_WhenFilmIsInactive() {
        boolean expectedStatus = true;
        List<Film> expectedFilms = new ArrayList<>();
        Film film = new Film();
        film.setDateStart(LocalDate.now().minusDays(10));
        film.setDateEnd(LocalDate.now().minusDays(1));
        expectedFilms.add(film);
        Mockito.when(filmRepository.findAll()).thenReturn(expectedFilms);
        List<Film> films = filmService.findFilmsIsActive(expectedStatus);
        assertTrue(films.isEmpty(), "Collection should be empty for inactive films");
    }


    @Test
    void ShouldGetAllFilm() {
        Mockito.when(filmRepository.findAll()).thenReturn(loadedFilms);
        List<Film> films = filmService.getAll();
        assertNotNull(films, "Collection should be not null");
        assertEquals(loadedFilms.size(), films.size(), "Sizes list of shares should be match");
    }

    @Test
    void shouldGetFilm_ById() {
        Long expectedId = 1L;
        Film expectedFilm = loadedFilms.stream()
                .filter(f -> f.getId().equals(expectedId))
                .findFirst()
                .orElse(null);
        Mockito.when(filmRepository.findById(expectedId)).thenReturn(Optional.ofNullable(expectedFilm));
        Optional<Film> filmById = filmService.getById(expectedId);
        assertTrue(filmById.isPresent(), "Film should not be null");
        assertEquals(expectedId, filmById.get().getId(), "Hall id should match");
    }

    @Test
    void shouldGetAllFilms_ByToday() {
        List<Film> expectedFilms = loadedFilms.stream()
                .filter(f -> f.getDateStart().isBefore(LocalDate.now()) && f.getDateEnd().isAfter(LocalDate.now()))
                .toList();
        Mockito.when(filmRepository.findAllByDateStartBeforeAndDateEndAfter(LocalDate.now(), LocalDate.now()))
                .thenReturn(expectedFilms);
        List<Film> films = filmService.getAllFilmsToday();
        assertNotNull(films, "Collection should not be null");
        assertEquals(expectedFilms.size(), films.size(), "Sizes list of films should be match");
    }
}