package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.repository.CinemaRepository;
import com.example.kinocms_user.service.CinemaService;
import com.example.kinocms_user.util.LogUtil;
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
        LogUtil.logGetAllNotification("cinemas");
        List<Cinema> cinemas = cinemaRepository.findAll();
        LogUtil.logSizeInfo("cinemas", cinemas.size());
        return cinemas;
    }

    @Override
    public Optional<Cinema> getById(Long id) {
        LogUtil.logGetNotification("cinema", "id", id);
        Optional<Cinema> cinemaById = cinemaRepository.findById(id);
        LogUtil.logGetInfo("Cinema", "id", id, cinemaById.isPresent());
        return cinemaById;
    }
}
