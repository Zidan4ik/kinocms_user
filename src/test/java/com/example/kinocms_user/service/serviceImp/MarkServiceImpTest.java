package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.Mark;
import com.example.kinocms_user.repository.MarkRepository;
import com.example.kinocms_user.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarkServiceImpTest {
    @Mock
    private MarkRepository markRepository;
    @InjectMocks
    private MarkServiceImp markService;
    private List<Mark> loadedMarks;

    @BeforeEach
    void setUp() {
        loadedMarks = TestDataUtil.loadMarks();
    }

    @Test
    void getAll() {
        when(markRepository.findAll()).thenReturn(loadedMarks);
        List<Mark> marks = markService.getAll();
        assertEquals(loadedMarks.size(),marks.size(),"Collection should have common sizes");
    }

    @Test
    void getAllByFilms() {
        List<Film> films = List.of(new Film(1L,"http://example.com/ceo", "image1.jpg", "http://example.com/trailer", LocalDate.now(), LocalDate.now().plusDays(30), 2024, LocalTime.of(2, 0), new BigDecimal("1000000")),
                new Film(2L, "http://example.com/ceo2", "image2.jpg", "http://example.com/trailer2", LocalDate.now().minusDays(10), LocalDate.now().plusDays(20), 2023, LocalTime.of(1, 30), new BigDecimal("500000")));

        List<Mark> expectedMarks = loadedMarks;
        when(markRepository.getAllByFilms(List.of(1L,2L))).thenReturn(expectedMarks);
        List<Mark> marks = markService.getAllByFilms(films);

        assertNotNull(marks,"Collection should not empty");
        assertEquals(expectedMarks.size(), marks.size(), "Collection should have common sizes");
        assertTrue(marks.containsAll(expectedMarks),"Actual marks should contain all expected marks");
    }
}