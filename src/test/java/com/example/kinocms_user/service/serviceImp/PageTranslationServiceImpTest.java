package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.enums.PageType;
import com.example.kinocms_user.repository.PageTranslatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PageTranslationServiceImpTest {
    @Mock
    private PageTranslatorRepository pageTranslatorRepository;
    @InjectMocks
    private PageTranslationServiceImp translationService;
    private List<PageTranslation> loadedTranslators;

    @BeforeEach
    void setUp() {
        loadedTranslators = loadedPagesTranslations();
    }

    @Test
    void testGetFilm_ValidFilmAndCode_ReturnPageTranslation() {
        LanguageCode expectedCode = LanguageCode.Ukr;
        Film expectedFilm = new Film();
        expectedFilm.setId(1L);

        PageTranslation translator = loadedTranslators.stream()
                .filter(p ->
                        p.getLanguageCode().equals(expectedCode) &&
                                p.getFilm() != null &&
                                p.getFilm().getId().equals(expectedFilm.getId())
                )
                .findFirst()
                .orElse(null);
        Mockito.when(pageTranslatorRepository
                        .getPageTranslationByFilmAndLanguageCode(expectedFilm, expectedCode))
                .thenReturn(Optional.ofNullable(translator));

        Optional<PageTranslation> translatorByFilmAndCode = translationService.getFilm(expectedFilm, expectedCode);

        assertNotNull(translator, "Translator should be found in loadedTranslators");
        assertTrue(translatorByFilmAndCode.isPresent(), "Translator should be present");
        assertEquals(translator.getId(), translatorByFilmAndCode.get().getId(), "Id should match");
        assertEquals(expectedCode, translatorByFilmAndCode.get().getLanguageCode(), "Language code should match");
        assertEquals(expectedFilm.getId(), translatorByFilmAndCode.get().getFilm().getId(), "Film id should match");
    }

    @Test
    void testGetCinema_ValidCinemaAndCode_ReturnPageTranslation() {
        LanguageCode expectedCode = LanguageCode.Eng;
        Cinema expectedCinema = new Cinema();
        expectedCinema.setId(2L);

        PageTranslation translator = loadedTranslators.stream()
                .filter(p ->
                        p.getLanguageCode().equals(expectedCode) &&
                                p.getCinema() != null &&
                                p.getCinema().getId().equals(expectedCinema.getId())
                )
                .findFirst()
                .orElse(null);
        Mockito.when(pageTranslatorRepository
                        .getPageTranslationByCinemaAndLanguageCode(expectedCinema, expectedCode))
                .thenReturn(Optional.ofNullable(translator));

        Optional<PageTranslation> translatorByCinemaAndCode = translationService.getCinema(expectedCinema, expectedCode);

        assertNotNull(translator, "Translator should be found in database");
        assertTrue(translatorByCinemaAndCode.isPresent(), "Translator should be present");
        assertEquals(translator.getId(), translatorByCinemaAndCode.get().getId(), "Id should match");
        assertEquals(expectedCode, translatorByCinemaAndCode.get().getLanguageCode(), "Language code should match");
        assertEquals(expectedCinema.getId(), translatorByCinemaAndCode.get().getCinema().getId(), "Cinema id should match");

    }

    private List<PageTranslation> loadedPagesTranslations() {
        Film f1 = new Film();
        f1.setId(1L);
        Cinema c1 = new Cinema();
        c1.setId(2L);
        PageTranslation p1 = new PageTranslation(LanguageCode.Ukr, PageType.film, "Людина павук", "Опис...", null, f1);
        p1.setId(1L);
        PageTranslation p2 = new PageTranslation(LanguageCode.Eng, PageType.cinema, "Cinema Ukraine", "desc...", null, c1);
        p2.setId(2L);
        return List.of(p1, p2);
    }
}