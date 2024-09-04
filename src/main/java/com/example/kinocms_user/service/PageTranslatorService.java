package com.example.kinocms_user.service;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;

import java.util.Optional;

public interface PageTranslatorService {
    Optional<PageTranslation> getFilm(Film film, LanguageCode code);
    Optional<PageTranslation> getCinema(Cinema cinema, LanguageCode code);
}
