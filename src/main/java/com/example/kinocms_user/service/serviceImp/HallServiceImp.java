package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Hall;
import com.example.kinocms_user.repository.HallRepository;
import com.example.kinocms_user.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HallServiceImp implements HallService {
    private final HallRepository hallRepository;
    @Override
    public List<Hall> getAll() {
        return hallRepository.findAll();
    }

    @Override
    public Optional<Hall> getById(Long id) {
        return hallRepository.findById(id);
    }

    @Override
    public List<Hall> getAllByCinema(Cinema cinema) {
        return hallRepository.getAllByCinema(cinema);
    }
}
