package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Hall;
import com.example.kinocms_user.repository.HallRepository;
import com.example.kinocms_user.service.HallService;
import com.example.kinocms_user.util.LogUtil;
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
        LogUtil.logGetAllNotification("halls");
        List<Hall> halls = hallRepository.findAll();
        LogUtil.logSizeInfo("halls", halls.size());
        return halls;
    }

    @Override
    public Optional<Hall> getById(Long id) {
        LogUtil.logGetNotification("hall", "id", id);
        Optional<Hall> hallById = hallRepository.findById(id);
        LogUtil.logGetInfo("Hall", "id", id, hallById.isPresent());
        return hallById;
    }

    @Override
    public List<Hall> getAllByCinema(Cinema cinema) {
        LogUtil.logGetAllNotification("halls", "cinema", cinema);
        List<Hall> hallByCinemas = hallRepository.getAllByCinema(cinema);
        LogUtil.logSizeInfo("halls", hallByCinemas.size());
        return hallByCinemas;
    }
}
