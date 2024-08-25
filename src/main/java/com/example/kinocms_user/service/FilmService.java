package com.example.kinocms_user.service;

import com.example.kinocms_user.entity.Film;

import java.time.LocalDate;
import java.util.List;

public interface FilmService {
    List<Film> findFilmsIsActive(boolean isActive);
    List<Film> getAll();
}
