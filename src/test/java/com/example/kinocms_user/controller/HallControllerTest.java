package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.Hall;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.service.HallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class HallControllerTest {
    @Mock
    private HallService hallService;
    @InjectMocks
    private HallController hallController;
    @MockBean
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(hallController).build();
    }

    @Test
    void shouldReturnCinemaView_WhenRequestIsMade() throws Exception {
        Long expectedId = 1L;
        ResultActions response = mockMvc.perform(get("/user/hall/{id}", expectedId));
        response.andExpect(status().isOk())
                .andExpect(view().name("pages/hall"))
                .andExpect(model().attribute("id", expectedId))
                .andDo(print());
    }

    @Test
    void shouldReturnHall_ById_WhenHallIsExist() throws Exception {
        Hall hall = new Hall(1L, 1, "Schema 1", "Banner 1",
                "http://example.com/ceo1", LocalDate.now(),
                null, null, null, null);
        hall.setPageTranslations(List.of(
                new PageTranslation(LanguageCode.Ukr, PageType.hall, "Зал 1", "Опис залу 1...", hall)
        ));
        when(hallService.getById(1L)).thenReturn(Optional.of(hall));

        ResultActions response = mockMvc.perform(get("/user/hall/{id}/data", 1L));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.number").value(1))
                .andDo(print());
    }
}