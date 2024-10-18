package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.*;
import com.example.kinocms_user.enums.BannerType;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.service.BannerService;
import com.example.kinocms_user.service.ShareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.kinocms_user.util.TestDataUtil.getElements;
import static com.example.kinocms_user.util.TestDataUtil.loadMarks;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ShareControllerTest {
    @Mock
    private ShareService shareService;
    @Mock
    private BannerService bannerService;
    @InjectMocks
    private ShareController shareController;
    private MockMvc mockMvc;
    private List<Share> expectedShares;
    private List<Banner> expectedBanners;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(shareController).build();
        Share share1 = new Share(1L, "image1.jpg", "banner1.jpg", LocalDate.of(2022, 5, 1), true, "url1",
                List.of(new PageTranslation(1L, LanguageCode.Ukr, PageType.share, "Нічна п'ятниця", "бла бла", null)),
                List.of(new CeoBlock(1L, LanguageCode.Ukr, PageType.share, "title1", "keywords1", "description1")),
                getElements(loadMarks()));
        Share share2 = new Share(2L, "image2.jpg", "banner2.jpg", LocalDate.of(2022, 6, 1), false, "url2",
                List.of(new PageTranslation(2L, LanguageCode.Ukr, PageType.share, "Дика неділя", "бла2 бла2", null)),
                List.of(new CeoBlock(2L, LanguageCode.Ukr, PageType.share, "title2", "keywords2", "description2")),
                getElements(loadMarks()));
        expectedShares = List.of(share1, share2);
        Banner banner1 = new Banner(1L, 5, true, BannerType.main, new ArrayList<>());
        Banner banner2 = new Banner(2L, 10, false, BannerType.main, new ArrayList<>());
        Banner banner3 = new Banner(3L, 7, true, BannerType.main, new ArrayList<>());
        expectedBanners = List.of(banner1, banner2, banner3);
    }

    @Test
    void shouldReturnSharesView_WhenRequestIsMade() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/shares"));
        response.andExpectAll(
                        status().isOk(),
                        view().name("pages/shares")
                )
                .andDo(print());
    }

    @Test
    void shouldGetShares_WhenListIsNotEmpty() throws Exception {
        boolean expectedStatus = true;
        when(shareService.getAllByStatus(expectedStatus)).thenReturn(expectedShares);
        ResultActions response = mockMvc.perform(get("/user/shares/data"));
        response.andExpectAll(
                        status().isOk(),
                        jsonPath("$.size()").value(expectedShares.size()),
                        jsonPath("$[0].id").value(expectedShares.get(0).getId()),
                        jsonPath("$[1].id").value(expectedShares.get(1).getId())
                )
                .andDo(print());
    }

    @Test
    void shouldGetBanners_WhenListIsNotEmpty() throws Exception {
        when(bannerService.getAll()).thenReturn(expectedBanners);
        ResultActions response = mockMvc.perform(get("/user/banners/data"));
        response.andExpectAll(
                        status().isOk(),
                        jsonPath("$.size()").value(expectedBanners.size()),
                        jsonPath("$[0].id").value(expectedBanners.get(0).getId()),
                        jsonPath("$[1].id").value(expectedBanners.get(1).getId())
                )
                .andDo(print());
    }

    @Test
    void showShare() throws Exception {
        Long expectedId = 1L;
        ResultActions response = mockMvc.perform(get("/user/share/{id}", expectedId));
        response.andExpectAll(
                        status().isOk(),
                        view().name("pages/share"),
                        model().attribute("id", expectedId)
                )
                .andDo(print());
    }

    @Test
    void getShare() throws Exception {
        Long expectedId = 1L;
        when(shareService.getById(expectedId)).thenReturn(Optional.of(expectedShares.get(0)));
        ResultActions response = mockMvc.perform(get("/user/share/{id}/data", expectedId));
        response.andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(expectedId)
                )
                .andDo(print());
    }
}