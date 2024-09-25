package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.CeoBlock;
import com.example.kinocms_user.entity.Contact;
import com.example.kinocms_user.entity.Page;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.service.ContactService;
import com.example.kinocms_user.service.PageService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class PageControllerTest {
    @Mock
    private PageService pageService;
    @Mock
    private ContactService contactService;
    @InjectMocks
    private PageController pageController;
    @MockBean
    private MockMvc mockMvc;
    private Page page;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pageController).build();
        page = new Page(true, PageType.about, LocalDate.of(2023, 9, 23));
        page.setId(1L);
        page.setPageTranslations(
                List.of(
                        new PageTranslation(LanguageCode.Ukr, PageType.film, "Сторінка про заклад",
                                "Опис про заклад...", page)
                )
        );
    }

    @Test
    void shouldReturnPageView_WhenTypeIsContacts() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/{type}", PageType.contacts));
        response.andExpect(status().isOk())
                .andExpect(view().name("pages/contacts"))
                .andDo(print());
    }

    @Test
    void shouldReturnPageView_WhenTypeIsNotContacts() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/{type}", PageType.cinema));
        response.andExpect(status().isOk())
                .andExpect(view().name("pages/page"))
                .andDo(print());
    }

    @Test
    void shouldReturnPageView_WhenTypeIsAdditional() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/{type}", PageType.additional));
        response.andExpect(status().isOk())
                .andExpect(view().name("user/additional"))
                .andDo(print());
    }

    @Test
    void shouldReturnPageView_ById() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/additional/{id}", 1L));
        response.andExpect(status().isOk())
                .andExpect(view().name("pages/page"))
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    void shouldGetDataPage_ByTypeAndId() throws Exception {
        when(pageService.getById(1L)).thenReturn(Optional.of(page));
        ResultActions response = mockMvc.perform(get("/user/{type}/{id}/data", PageType.about, 1L));
        response.andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.type").value("about")
                )
                .andDo(print());
    }

    @Test
    void shouldGetDataPage_ByType() throws Exception {
        PageType expectedType = PageType.about;
        when(pageService.getByType(expectedType)).thenReturn(Optional.of(page));
        ResultActions response = mockMvc.perform(get("/user/{type}/data", expectedType));
        response.andExpectAll(
                status().isOk(),
                jsonPath("$.id").value(1L),
                jsonPath("$.type").value("about")
        ).andDo(print());
    }

    @Test
    void shouldGetPagesMenu() throws Exception {
        List<Page> expectedPages = List.of(page);
        when(pageService.getAll()).thenReturn(expectedPages);
        ResultActions response = mockMvc.perform(get("/user/pages/data"));
        response.andExpectAll(
                status().isOk(),
                jsonPath("$.size()").value(1),
                jsonPath("$[0].id").value(1L),
                jsonPath("$[0].type").value("about")
        ).andDo(print());
    }

    @Test
    void shouldGetContacts() throws Exception {
        Contact contact1 = new Contact(1L, "John Doe", "123 Main Street, Springfield",
                "45.4215,-75.6972", "john_logo.png", new Page()
        );
        Contact contact2 = new Contact(
                2L, "Jane Smith", "456 Elm Street, Gotham",
                "40.7128,-74.0060", "jane_logo.png", new Page()
        );

        when(contactService.getAll()).thenReturn(List.of(contact1,contact2));
        ResultActions response = mockMvc.perform(get("/user/contacts/data"));
        response.andExpectAll(
                status().isOk(),
                jsonPath("$.size()").value(2),
                jsonPath("$[0].id").value(1L)
        ).andDo(print());
    }

    @Test
    void shouldGetPageOfMain() throws Exception {
        PageType expectedType = PageType.main;
        page.setPhoneFirst("0982388123");
        page.setPhoneSecond("0552323812");
        page.setCeoBlocks(
                List.of(
                        new CeoBlock()
                )
        );
        when(pageService.getByType(expectedType)).thenReturn(Optional.of(page));
        ResultActions response = mockMvc.perform(get("/user/main/data", expectedType));
        response.andExpectAll(
                status().isOk(),
                jsonPath("$.id").value(1L),
                jsonPath("$.type").value("main")
        ).andDo(print());
    }

    @Test
    void showMainPage() {
    }
}