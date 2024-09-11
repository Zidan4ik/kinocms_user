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
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String showPosterMovies() {
        return "pages/poster";
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
        return "pages/soon";
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

    @GetMapping("/movie/{id}")
    public ModelAndView showMovie(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("pages/movie");
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
                List<Mark> marks = markService.getAllByFilms(Collections.singletonList(filmById.get()));
                return FilmMapper.toDTO(filmById.get(), translatorUk.get(), marks);
            }
        }
        return null;
    }

    @GetMapping("/timetable")
    public ModelAndView showTimetable() {
        ModelAndView model = new ModelAndView("pages/timetable");
        List<FilmDTO> filmDTOS = new ArrayList<>();
        for (Film film : filmService.getAllFilmsToday()) {
            Optional<PageTranslation> translatorUKR = pageTranslatorService.getFilm(film, LanguageCode.Ukr);
            if (translatorUKR.isPresent()) {
                List<Mark> marks = markService.getAllByFilms(Collections.singletonList(film));
                FilmDTO filmDTO = FilmMapper.toDTO(film, translatorUKR.get(), marks);
                filmDTOS.add(filmDTO);
            }
        }
        model.addObject("filmsToday", filmDTOS);
        return model;
    }

    @GetMapping("/movie/{id}/buy")
    public ModelAndView showBuyMovie(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("pages/movie-buy");
        Optional<Film> movieById = filmService.getById(id);
        FilmDTO filmDTO = null;
        if (movieById.isPresent()) {
            Optional<PageTranslation> translatorUKR = pageTranslatorService.getFilm(movieById.get(), LanguageCode.Ukr);
            if (translatorUKR.isPresent()) {
                filmDTO = FilmMapper.toDTO(movieById.get(), translatorUKR.get(), null);
            }
        }
        model.addObject("movie", filmDTO);
        return model;
    }
}



