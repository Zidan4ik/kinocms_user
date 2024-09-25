package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Page;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.repository.PageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PageServiceImpTest {
    @Mock
    private PageRepository pageRepository;
    @InjectMocks
    private PageServiceImp pageService;
    private List<Page> loadedPages;

    @BeforeEach
    void setUp() {
        loadedPages = loadPages();
    }

    @Test
    void getAll() {
        when(pageRepository.findAll()).thenReturn(loadedPages);
        List<Page> pages = pageService.getAll();
        assertNotNull(pages, "Collection with pages should be not null");
        assertEquals(loadedPages.size(), pages.size(), "Sizes list of shares should be match");
    }

    @Test
    void getById() {
        Long expectedId = 1L;
        Page page = loadedPages.stream()
                .filter(p -> p.getId().equals(expectedId))
                .findFirst()
                .orElse(null);

        when(pageRepository.findById(expectedId)).thenReturn(Optional.ofNullable(page));

        Optional<Page> pageById = pageService.getById(expectedId);
        assertTrue(pageById.isPresent(), "Page should present");
        assertEquals(expectedId, pageById.get().getId(), "Id should match");
    }

    @Test
    void getByType() {
        PageType expectedType = PageType.cinema;
        Page page = loadedPages.stream()
                .filter(p -> p.getType().equals(expectedType))
                .findFirst()
                .orElse(null);

        when(pageRepository.findByType(expectedType)).thenReturn(Optional.ofNullable(page));

        Optional<Page> pageByType = pageService.getByType(expectedType);
        assertTrue(pageByType.isPresent(), "Page should present");
        assertEquals(expectedType, pageByType.get().getType(), "Type should match");
    }

    private List<Page> loadPages() {
        Page page1 = new Page(true, PageType.film, LocalDate.of(2023, 9, 23));
        page1.setId(1L);
        Page page2 = new Page(false, PageType.cinema, LocalDate.of(2023, 8, 15));
        page2.setId(2L);
        Page page3 = new Page(true, PageType.hall, LocalDate.of(2023, 7, 10));
        page3.setId(3L);
        return List.of(page1, page2, page3);
    }
}