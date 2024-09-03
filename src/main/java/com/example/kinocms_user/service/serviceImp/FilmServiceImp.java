package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.repository.FilmRepository;
import com.example.kinocms_user.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmServiceImp implements FilmService {
    private final FilmRepository filmRepository;

    @Override
    public List<Film> findFilmsIsActive(boolean status) {
        List<Film> films = new ArrayList<>();
        LocalDate today = LocalDate.now();
        if (status) {
            for (Film film : getAll()) {
                if ((film.getDateStart().isBefore(today) || film.getDateStart().equals(today)) &&
                        (film.getDateEnd().isAfter(today) || film.getDateEnd().equals(today))) {
                    films.add(film);
                }
            }
        } else {
            for (Film film : getAll()) {
                if (film.getDateStart().isAfter(today) && film.getDateEnd().isAfter(today)) {
                    films.add(film);
                }
            }
        }
        return films;
    }

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public Optional<Film> getById(Long id) {
        return filmRepository.findById(id);
    }
    @Override
    public List<Film> getAllFilmsToday(){
        return filmRepository.findAllByDateStartBeforeAndDateEndAfter(LocalDate.now(), LocalDate.now());
    }
}
