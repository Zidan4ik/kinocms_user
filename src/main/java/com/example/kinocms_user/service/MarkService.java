package com.example.kinocms_user.service;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.Mark;

import java.util.List;

public interface MarkService {
    List<Mark> getAll();
    List<Mark> getAllByFilms(List<Film> films);
}
