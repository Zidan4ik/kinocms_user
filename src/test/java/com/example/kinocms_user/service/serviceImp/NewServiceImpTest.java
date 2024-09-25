package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.New;
import com.example.kinocms_user.repository.NewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewServiceImpTest {
    @Mock
    private NewRepository newRepository;
    @InjectMocks
    private NewServiceImp newService;
    private List<New> loadedNews;

    @BeforeEach
    void setUp() {
        loadedNews = loadNews();
    }

    @Test
    void getAll() {
        when(newRepository.findAll()).thenReturn(loadedNews);
        List<New> news = newService.getAll();
        assertNotNull(news,"Collection news should not null");
        assertEquals(loadedNews.size(),news.size(),"Size between collections should match");
    }

    private List<New> loadNews() {
        New new1 = new New(null, "image1.jpg", LocalDate.now(), true, "http://example.com/1", null, null, new HashSet<>());
        New new2 = new New(null, "image2.jpg", LocalDate.now(), false, "http://example.com/2", null, null, new HashSet<>());
        New new3 = new New(null, "image3.jpg", LocalDate.now(), true, "http://example.com/3", null, null, new HashSet<>());
        return List.of(new1, new2, new3);
    }
}