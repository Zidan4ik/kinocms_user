package com.example.kinocms_user.service;

import com.example.kinocms_user.entity.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<Film> findFilmsIsActive(boolean isActive);
    List<Film> getAll();
    Optional<Film> getById(Long id);
    List<Film> getAllFilmsToday();
}
