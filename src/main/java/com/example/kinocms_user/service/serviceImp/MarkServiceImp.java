package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.Mark;
import com.example.kinocms_user.repository.MarkRepository;
import com.example.kinocms_user.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImp implements MarkService {
    private final MarkRepository markRepository;
    @Override
    public List<Mark> getAll() {
        return markRepository.findAll();
    }

    @Override
    public List<Mark> getAllByFilms(List<Film> films) {
        List<Long> ids = films.stream()
                .map(Film::getId)
                .toList();
        return markRepository.getAllByFilms(ids);
    }
}
