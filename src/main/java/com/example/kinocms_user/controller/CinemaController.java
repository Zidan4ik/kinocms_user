package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Hall;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.mapper.CinemaMapper;
import com.example.kinocms_user.model.CinemaDTO;
import com.example.kinocms_user.model.CinemasDTO;
import com.example.kinocms_user.service.CinemaService;
import com.example.kinocms_user.service.HallService;
import com.example.kinocms_user.service.PageTranslatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class CinemaController {
    private final CinemaService cinemaService;
    private final HallService hallService;
    private final PageTranslatorService pageTranslatorService;

    @GetMapping("/cinemas")
    public String showCinemas() {
        return "pages/cinemas";
    }

    @GetMapping("/cinemas-data")
    @ResponseBody
    public List<CinemasDTO> getCinemas() {
        return CinemaMapper.toDTOList(cinemaService.getAll());
    }

    @GetMapping("/cinema/{id}")
    public ModelAndView showCinema(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("pages/cinema");
        model.addObject("id", id);
        return model;
    }

    @GetMapping("/cinema/{id}/data")
    @ResponseBody
    public CinemaDTO getCinema(@PathVariable Long id) {
        Optional<Cinema> cinemaId = cinemaService.getById(id);
        if(cinemaId.isPresent()){
            Optional<PageTranslation> translatorUKR = pageTranslatorService.getCinema(cinemaId.get(), LanguageCode.Ukr);
            if(translatorUKR.isPresent()){
                List<Hall> hallByCinema = hallService.getAllByCinema(cinemaId.get());
                return CinemaMapper.toDTOCinema(cinemaId.get(),translatorUKR.get(),hallByCinema);
            }
        }
        return null;
    }
}
