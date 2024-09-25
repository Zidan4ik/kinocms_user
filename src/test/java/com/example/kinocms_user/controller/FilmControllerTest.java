package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.*;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.service.FilmService;
import com.example.kinocms_user.service.MarkService;
import com.example.kinocms_user.service.PageTranslatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static com.example.kinocms_user.util.TestDataUtil.loadFilms;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {
    @Mock
    private FilmService filmService;
    @Mock
    private PageTranslatorService pageTranslatorService;
    @Mock
    private MarkService markService;
    @InjectMocks
    private FilmController filmController;
    private MockMvc mockMvc;
    private Film film;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(filmController).build();
        film = new Film(1L, "https://example.com/ceo", "image.jpg", "https://example.com/trailer",
                LocalDate.of(2024, 9, 25), LocalDate.of(2024, 10, 10),
                2024, LocalTime.of(2, 30), new BigDecimal("10000000.00"),
                List.of(new CeoBlock()),
                List.of(new PageTranslation()),
                Set.of(new Genre()),
                Set.of(new Mark()),
                List.of(new Gallery()));
        film.setPageTranslations(List.of(
                new PageTranslation(LanguageCode.Ukr, PageType.film, "Людина павук", "Опис...", null, film)
        ));
        film.setMarksList(Set.of(
                new Mark(1L, "18+"),
                new Mark(2L, "VIP"),
                new Mark(3L, "NO SEX")
        ));

    }

    @Test
    void shouldPosterMoviesView_WhenRequiresIsMade() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/poster"));
        response.andExpect(status().isOk())
                .andExpect(view().name("pages/poster"))
                .andDo(print());
    }

    @Test
    void shouldPosterMovies_WithCorrectSize_WhenRequestIsMade() throws Exception {
        List<Film> expectedFilms = List.of(film);
        when(filmService.findFilmsIsActive(true)).thenReturn(expectedFilms);
        for (Film f : expectedFilms) {
            if (f.getPageTranslations() != null) {
                for (PageTranslation t : f.getPageTranslations()) {
                    when(pageTranslatorService.getFilm(f, t.getLanguageCode())).thenReturn(Optional.of(t));
                }
                when(markService.getAllByFilms(List.of(f))).thenReturn(
                        new ArrayList<>(f.getMarksList())
                );
            }
        }

        ResultActions response = mockMvc.perform(get("/user/poster/data"));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].marks.size()").value(3))
                .andDo(print());

        verify(filmService, timeout(1)).findFilmsIsActive(true);
        verify(pageTranslatorService, timeout(1)).getFilm(film, LanguageCode.Ukr);
        verify(markService, timeout(1)).getAllByFilms(expectedFilms);
    }

    @Test
    void shouldSoonMoviesView_WhenRequiresIsMade() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/soon"));
        response.andExpect(status().isOk())
                .andExpect(view().name("pages/soon"))
                .andDo(print());
    }

    @Test
    void shouldSoonMovies_WithCorrectSize_WhenRequestIsMade() throws Exception {
        List<Film> expectedFilms = List.of(film);
        when(filmService.findFilmsIsActive(false)).thenReturn(expectedFilms);

        for (Film f : expectedFilms) {
            if (f.getPageTranslations() != null) {
                for (PageTranslation t : f.getPageTranslations()) {
                    when(pageTranslatorService.getFilm(f, t.getLanguageCode())).thenReturn(Optional.of(t));
                }
                when(markService.getAllByFilms(List.of(f))).thenReturn(
                        new ArrayList<>(f.getMarksList())
                );
            }
        }

        ResultActions response = mockMvc.perform(get("/user/soon/data"));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].marks.size()").value(3))
                .andDo(print());

        verify(filmService, timeout(1)).findFilmsIsActive(false);
        verify(pageTranslatorService, timeout(1)).getFilm(film, LanguageCode.Ukr);
        verify(markService, timeout(1)).getAllByFilms(List.of(film));
    }

    @Test
    void shouldMovieView_WithValidId_ThenViewHasCorrectId() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/movie/{id}", 1L));
        response.andExpect(status().isOk())
                .andExpect(view().name("pages/movie"))
                .andExpect(model().attribute("id", 1L))
                .andDo(print());
    }

    @Test
    void shouldGetFilm_WithId_WhenFilmIsPresent() throws Exception {
        when(filmService.getById(1L)).thenReturn(Optional.of(film));
        for (PageTranslation t : film.getPageTranslations()) {
            when(pageTranslatorService.getFilm(film, LanguageCode.Ukr)).thenReturn(Optional.of(t));
        }
        when(markService.getAllByFilms(List.of(film))).thenReturn(new ArrayList<>(film.getMarksList()));

        ResultActions response = mockMvc.perform(get("/user/movie/{id}/data", 1L));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.marks.size()").value(3))
                .andDo(print());
        verify(filmService, timeout(1)).getById(1L);
        verify(pageTranslatorService, timeout(1)).getFilm(film, LanguageCode.Ukr);
        verify(markService, timeout(1)).getAllByFilms(List.of(film));
    }

    @Test
    void shouldGetFilm_WithId_WhenFilmIsNotPresent() throws Exception {
        when(filmService.getById(2L)).thenReturn(Optional.empty());
        ResultActions response = mockMvc.perform(get("/user/movie/{id}/data", 2L));
        response.andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
        verify(filmService, timeout(1)).getById(2L);
    }

    @Test
    void shouldGetFilm_WithId_WhenPageTranslationNotPresent() throws Exception {
        when(filmService.getById(1L)).thenReturn(Optional.of(film));
        for (PageTranslation t : film.getPageTranslations()) {
            when(pageTranslatorService.getFilm(film, LanguageCode.Ukr)).thenReturn(Optional.empty());
        }
        ResultActions response = mockMvc.perform(get("/user/movie/{id}/data", 1L));
        response.andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
        verify(filmService, timeout(1)).getById(1L);
        verify(pageTranslatorService, timeout(1)).getFilm(film, LanguageCode.Ukr);
    }

    @Test
    void shouldShowTimeTable_WithFilmsExist() throws Exception {
        List<Film> expectedFilms = List.of(film);
        when(filmService.getAllFilmsToday()).thenReturn(expectedFilms);
        for (Film f : expectedFilms) {
            if (f.getPageTranslations() != null) {
                for (PageTranslation t : f.getPageTranslations()) {
                    when(pageTranslatorService.getFilm(f, t.getLanguageCode())).thenReturn(Optional.of(t));
                }
            }
            when(markService.getAllByFilms(List.of(f))).thenReturn(new ArrayList<>(f.getMarksList()));
        }
        ResultActions response = mockMvc.perform(get("/user/timetable"));
        response.andExpect(status().isOk())
                .andExpect(model().attributeExists("filmsToday"))
                .andExpect(model().attribute("filmsToday", hasSize(expectedFilms.size())))
                .andDo(print());
    }

    @Test
    void shouldShowTimeTable_WithFilmsExist_WhenPageTranslationIsNotPresent() throws Exception {
        List<Film> expectedFilms = List.of(film);
        when(filmService.getAllFilmsToday()).thenReturn(expectedFilms);
        for (Film f : expectedFilms) {
            if (f.getPageTranslations() != null) {
                for (PageTranslation t : f.getPageTranslations()) {
                    when(pageTranslatorService.getFilm(f, t.getLanguageCode())).thenReturn(Optional.empty());
                }
            }
        }
        ResultActions response = mockMvc.perform(get("/user/timetable"));
        response.andExpect(status().isOk())
                .andExpect(model().attributeExists("filmsToday"))
                .andExpect(model().attribute("filmsToday", empty()))
                .andDo(print());
    }

    @Test
    void shouldByMovie_WithFilmExist() throws Exception {
        when(filmService.getById(1L)).thenReturn(Optional.of(film));
        for (PageTranslation p : film.getPageTranslations()) {
            when(pageTranslatorService.getFilm(film, p.getLanguageCode())).thenReturn(Optional.of(p));
        }
        ResultActions response = mockMvc.perform(get("/user/movie/{id}/buy", 1L));
        response.andExpect(status().isOk())
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attribute("movie", allOf(
                        hasProperty("id",is(film.getId()))
                )))
                .andDo(print());
    }

    @Test
    void shouldByMovie_WithFilmExist_WhenPageTranslationIsNotPresent() throws Exception {
        when(filmService.getById(1L)).thenReturn(Optional.of(film));
        for (PageTranslation p : film.getPageTranslations()) {
            when(pageTranslatorService.getFilm(film, p.getLanguageCode())).thenReturn(Optional.empty());
        }
        ResultActions response = mockMvc.perform(get("/user/movie/{id}/buy", 1L));
        response.andExpect(status().isOk())
                .andExpect(model().attribute("movie", nullValue()))
                .andDo(print());
    }

    @Test
    void shouldByMovie_WithFilmIsNotExist() throws Exception {
        when(filmService.getById(1L)).thenReturn(Optional.empty());
        ResultActions response = mockMvc.perform(get("/user/movie/{id}/buy", 1L));
        response.andExpect(status().isOk())
                .andExpect(model().attribute("movie", nullValue()))
                .andDo(print());
    }
}