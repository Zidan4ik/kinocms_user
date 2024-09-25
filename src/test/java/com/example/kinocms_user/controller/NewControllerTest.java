package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.New;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.service.NewService;
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
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class NewControllerTest {
    @Mock
    private NewService newService;
    @InjectMocks
    private NewController newController;
    @MockBean
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(newController).build();
    }

    @Test
    void shouldReturnNewsView_WhenRequestIsMade() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/news"));
        response.andExpect(status().isOk())
                .andExpect(view().name("pages/news"))
                .andDo(print());
    }

    @Test
    void shouldReturnNews_() throws Exception {
        New new1 = new New(1L, "image1.jpg", LocalDate.now(), true, "http://example.com/1", null, null, new HashSet<>());
        New new2 = new New(2L, "image2.jpg", LocalDate.now(), false, "http://example.com/2", null, null, new HashSet<>());

        new1.setPageTranslations(List.of(
                new PageTranslation(LanguageCode.Ukr, PageType.news, "Новина 1", "Опис новини 1...", new1)
        ));
        new2.setPageTranslations(List.of(
                new PageTranslation(LanguageCode.Ukr, PageType.news, "Новина 2", "Опис новини 2...", new2)
        ));

        when(newService.getAll()).thenReturn(List.of(new1, new2));
        ResultActions response = mockMvc.perform(get("/user/news/data"));
        response.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[0].id").value(1L),
                        jsonPath("$[1].id").value(2L)
                )
                .andDo(print());
    }
}