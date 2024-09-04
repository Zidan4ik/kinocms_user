package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.repository.CinemaRepository;
import com.example.kinocms_user.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CinemaServiceImp implements CinemaService {
    private final CinemaRepository cinemaRepository;

    @Override
    public List<Cinema> getAll() {
        return cinemaRepository.findAll();
    }

    @Override
    public Optional<Cinema> getById(Long id) {
        return cinemaRepository.findById(id);
    }
}
