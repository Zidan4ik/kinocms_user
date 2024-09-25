package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Hall;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.mapper.CinemaMapper;
import com.example.kinocms_user.model.CinemasDTO;
import com.example.kinocms_user.service.CinemaService;
import com.example.kinocms_user.service.HallService;
import com.example.kinocms_user.service.PageTranslatorService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


@ExtendWith(MockitoExtension.class)
class CinemaControllerTest {
    @Mock
    private CinemaService cinemaService;
    @Mock
    private HallService hallService;
    @Mock
    private PageTranslatorService pageTranslatorService;
    @InjectMocks
    private CinemaController cinemaController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cinemaController).build();
    }

    @Test
    void shouldReturnCinemasView_WhenRequestIsMade() throws Exception {
        ResultActions response = mockMvc
                .perform(get("/user/cinemas"));
        response.andExpect(status().isOk())
                .andExpect(view().name("pages/cinemas"))
                .andDo(print());
    }

    @Test
    void shouldReturnCinemas_WithCorrectSize_WhenRequestIsMade() throws Exception {
        Cinema cinema1 = new Cinema(1L, "cinemaLogo1.png", "cinemaBanner1.jpg",
                "https://cinema1.example.com", LocalDate.of(2017, 5, 15),
                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()
        );
        Cinema cinema2 = new Cinema(2L, "cinemaLogo2.png", "cinemaBanner2.jpg",
                "https://cinema2.example.com", LocalDate.of(2018, 5, 15),
                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()
        );
        List<Cinema> expectedCinemas = List.of(cinema1, cinema2);
        List<CinemasDTO> expectedCinemasDTOS = CinemaMapper.toDTOList(expectedCinemas);
        given(cinemaService.getAll()).willReturn(expectedCinemas);

        ResultActions response =
                mockMvc.perform(get("/user/cinemas-data"));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(expectedCinemasDTOS.size())))
                .andDo(print());
        verify(cinemaService, timeout(1)).getAll();
    }

    @Test
    void shouldReturnCinemaView_WhenIdIsValid_AndModelHasCorrectAttributes() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/cinema/{id}", 1L));
        response.andExpect(status().isOk())
                .andExpect(view().name("pages/cinema"))
                .andExpect(model().attribute("id", 1L))
                .andDo(print());
    }

    @Test
    void shouldGetCinema_WhenIdValid_AndModelHasCorrectAttributes() throws Exception {
        Cinema cinema1 = new Cinema(1L, "cinemaLogo1.png", "cinemaBanner1.jpg",
                "https://cinema1.example.com", LocalDate.of(2017, 5, 15),
                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()
        );
        PageTranslation pageTranslation = new PageTranslation();
        pageTranslation.setId(1L);
        pageTranslation.setTitle("Cinema name in Ukrainian");

        Hall hall = new Hall(1L, 1, "Schema 1", "Banner 1",
                "http://example.com/ceo1", LocalDate.now(), cinema1,
                null, null, null);
        PageTranslation page1 = new PageTranslation(LanguageCode.Ukr, PageType.film, "Людина павук", "Опис...", hall);
        hall.setPageTranslations(List.of(page1));

        List<Hall> halls = List.of(hall);

        when(cinemaService.getById(1L)).thenReturn(Optional.of(cinema1));
        when(pageTranslatorService.getCinema(cinema1, LanguageCode.Ukr)).thenReturn(Optional.of(pageTranslation));
        when(hallService.getAllByCinema(cinema1)).thenReturn(halls);

        ResultActions response = mockMvc.perform(get("/user/cinema/{id}/data", 1L));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nameCinema").value("Cinema name in Ukrainian"))
                .andExpect(jsonPath("$.hallDTOS[0].id").value(1))
                .andDo(print());
    }

    @Test
    void shouldGetCinema_WhenIdValid_AndModelHasUnCorrectPageTranslation() throws Exception {
        Cinema cinema1 = new Cinema(1L, "cinemaLogo1.png", "cinemaBanner1.jpg",
                "https://cinema1.example.com", LocalDate.of(2017, 5, 15),
                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()
        );
        when(cinemaService.getById(1L)).thenReturn(Optional.of(cinema1));

        ResultActions response = mockMvc.perform(get("/user/cinema/{id}/data", 1L));
        response.andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    void shouldGetCinema_WhenIdIsInvalid() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/cinema/{id}/data", 1L));
        response.andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }
}