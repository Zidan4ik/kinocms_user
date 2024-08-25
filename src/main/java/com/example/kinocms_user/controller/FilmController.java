package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.mapper.FilmMapper;
import com.example.kinocms_user.model.FilmDTO;
import com.example.kinocms_user.service.FilmService;
import com.example.kinocms_user.service.PageTranslatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class FilmController {
    private final FilmService filmService;
    private final PageTranslatorService pageTranslatorService;

    @GetMapping("/poster")
    public String showFilmPoster() {
        return "poster-page";
    }

    @GetMapping("/poster/data")
    @ResponseBody
    public List<FilmDTO> getFilmData() {
        List<Film> films = filmService.findFilmsIsActive(true);
        List<FilmDTO> dto = new ArrayList<>();
        for (Film film : films) {
            Optional<PageTranslation> translation = pageTranslatorService.getFilm(film, LanguageCode.Ukr);
            translation.ifPresent(pageTranslation -> dto.add(FilmMapper.toDTO(film, pageTranslation, null)));
        }
        return dto;
    }
}

