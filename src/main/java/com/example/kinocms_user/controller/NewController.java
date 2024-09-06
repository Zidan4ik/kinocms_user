package com.example.kinocms_user.controller;

import com.example.kinocms_user.mapper.NewMapper;
import com.example.kinocms_user.model.NewDTO;
import com.example.kinocms_user.service.NewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class NewController {
    private final NewService newService;
    @GetMapping("/news")
    public String showNews(){
        return "pages/news";
    }
    @GetMapping("/news/data")
    @ResponseBody
    public List<NewDTO> getNews(){
        return NewMapper.toDTOList(newService.getAll());
    }
}
