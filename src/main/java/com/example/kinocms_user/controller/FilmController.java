package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.Mark;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.mapper.FilmMapper;
import com.example.kinocms_user.model.FilmDTO;
import com.example.kinocms_user.service.FilmService;
import com.example.kinocms_user.service.MarkService;
import com.example.kinocms_user.service.PageTranslatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class FilmController {
    private final FilmService filmService;
    private final PageTranslatorService pageTranslatorService;
    private final MarkService markService;

    @GetMapping("/poster")
    public String showPosterMovies() {
        return "movie/poster-page";
    }

    @GetMapping("/poster/data")
    @ResponseBody
    public List<FilmDTO> getPosterMovies() {
        List<Film> films = filmService.findFilmsIsActive(true);
        List<FilmDTO> dto = new ArrayList<>();
        for (Film film : films) {
            Optional<PageTranslation> translation = pageTranslatorService.getFilm(film, LanguageCode.Ukr);
            List<Mark> marks = markService.getAllByFilms(Collections.singletonList(film));
            translation.ifPresent(pageTranslation -> dto.add(FilmMapper.toDTO(film, pageTranslation, marks)));
        }
        return dto;
    }

    @GetMapping("/soon")
    public String showSoonMovies() {
        return "movie/soon-page";
    }

    @GetMapping("/soon/data")
    @ResponseBody
    public List<FilmDTO> getSoonMovies() {
        List<Film> films = filmService.findFilmsIsActive(false);
        List<FilmDTO> dto = new ArrayList<>();
        for (Film film : films) {
            Optional<PageTranslation> translation = pageTranslatorService.getFilm(film, LanguageCode.Ukr);
            List<Mark> marks = markService.getAllByFilms(Collections.singletonList(film));
            translation.ifPresent(pageTranslation -> dto.add(FilmMapper.toDTO(film, pageTranslation, marks)));
        }
        return dto;
    }
}

