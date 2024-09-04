package com.example.kinocms_user.service;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Hall;

import java.util.List;
import java.util.Optional;

public interface HallService {
    List<Hall> getAll();
    Optional<Hall> getById(Long id);
    List<Hall> getAllByCinema(Cinema cinema);
}
