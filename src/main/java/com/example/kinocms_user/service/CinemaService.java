package com.example.kinocms_user.service;

import com.example.kinocms_user.entity.Cinema;

import java.util.List;
import java.util.Optional;

public interface CinemaService {
    List<Cinema> getAll();

    Optional<Cinema> getById(Long id);
}
