package com.example.kinocms_user.controller;

import com.example.kinocms_user.mapper.HallMapper;
import com.example.kinocms_user.model.HallDTO;
import com.example.kinocms_user.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class HallController {
    private final HallService hallService;
    @GetMapping("/hall/{id}")
    public String showHall(@PathVariable Long id){
        return "pages/hall";
    }
    @GetMapping("/hall/{id}/data")
    @ResponseBody
    public HallDTO getHall(@PathVariable Long id){
        return hallService.getById(id).map(HallMapper::toDTO).orElse(null);
    }
}
