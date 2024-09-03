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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
            translation.ifPresent(pageTranslation -> dto.add(FilmMapper.toDTO(film, pageTranslation)));
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
            translation.ifPresent(pageTranslation -> dto.add(FilmMapper.toDTO(film, pageTranslation)));
        }
        return dto;
    }

    @GetMapping("/movie/{id}")
    public ModelAndView showMovie(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("movie/movie-page");
        model.addObject("id", id);
        return model;
    }

    @GetMapping("/movie/{id}/data")
    @ResponseBody
    public FilmDTO getFilm(@PathVariable Long id) {
        Optional<Film> filmById = filmService.getById(id);
        if (filmById.isPresent()) {
            Optional<PageTranslation> translatorUk = pageTranslatorService.getFilm(filmById.get(), LanguageCode.Ukr);
            if (translatorUk.isPresent()) {
                return FilmMapper.toDTO(filmById.get(), translatorUk.get());
            }
        }
        return null;
    }

}



