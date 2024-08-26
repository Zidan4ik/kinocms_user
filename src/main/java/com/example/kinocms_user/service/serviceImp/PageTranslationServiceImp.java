package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.repository.PageTranslatorRepository;
import com.example.kinocms_user.service.PageTranslatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageTranslationServiceImp implements PageTranslatorService {
    private final PageTranslatorRepository pageTranslatorRepository;
    @Override
    public Optional<PageTranslation> getFilm(Film film, LanguageCode code) {
        return pageTranslatorRepository.getPageTranslationByFilmAndLanguageCode(film,code);
    }
}
