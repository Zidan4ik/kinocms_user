package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.repository.PageTranslatorRepository;
import com.example.kinocms_user.service.PageTranslatorService;
import com.example.kinocms_user.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageTranslationServiceImp implements PageTranslatorService {
    private final PageTranslatorRepository pageTranslatorRepository;

    @Override
    public Optional<PageTranslation> getFilm(Film film, LanguageCode code) {
        LogUtil.logGetNotification("translator", "film", "code", film, code);
        Optional<PageTranslation> translatorByFilmAndLanguageCode = pageTranslatorRepository.getPageTranslationByFilmAndLanguageCode(film, code);
        LogUtil.logGetInfo("Translator", "film", "code", film, code, translatorByFilmAndLanguageCode.isPresent());
        return translatorByFilmAndLanguageCode;
    }

    @Override
    public Optional<PageTranslation> getCinema(Cinema cinema, LanguageCode code) {
        LogUtil.logGetNotification("translator", "cinema", "code", cinema, code);
        Optional<PageTranslation> pageByCinemaAndLanguageCode = pageTranslatorRepository.getPageTranslationByCinemaAndLanguageCode(cinema, code);
        LogUtil.logGetInfo("Translator", "cinema", "code", cinema, code, pageByCinemaAndLanguageCode.isPresent());
        return pageByCinemaAndLanguageCode;
    }
}
