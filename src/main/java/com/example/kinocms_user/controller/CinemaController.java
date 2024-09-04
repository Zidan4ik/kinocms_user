package com.example.kinocms_user.controller;

import com.example.kinocms_user.mapper.CinemaMapper;
import com.example.kinocms_user.model.CinemasDTO;
import com.example.kinocms_user.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class CinemaController {
    private final CinemaService cinemaService;
    @GetMapping("/cinemas")
    public String showCinemas(){
        return "pages/cinemas-page";
    }
    @GetMapping("/cinemas-data")
    @ResponseBody
    public List<CinemasDTO> getCinemas(){
        return CinemaMapper.toDTOList(cinemaService.getAll());
    }
}
