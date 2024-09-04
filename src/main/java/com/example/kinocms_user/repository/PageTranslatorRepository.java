package com.example.kinocms_user.repository;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageTranslatorRepository extends JpaRepository<PageTranslation,Long> {
    Optional<PageTranslation> getPageTranslationByFilmAndLanguageCode(Film film, LanguageCode code);
    Optional<PageTranslation> getPageTranslationByCinemaAndLanguageCode(Cinema cinema, LanguageCode code);
}
