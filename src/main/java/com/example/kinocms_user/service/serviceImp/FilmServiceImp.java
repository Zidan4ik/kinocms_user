package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.repository.FilmRepository;
import com.example.kinocms_user.service.FilmService;
import com.example.kinocms_user.util.LogUtil;
import lombok.RequiredArgsConstructor;
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
        LogUtil.logGetAllNotification("films", "status", status);
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
        LogUtil.logSizeInfo("films", films.size());
        return films;
    }

    @Override
    public List<Film> getAll() {
        LogUtil.logGetAllNotification("films");
        List<Film> films = filmRepository.findAll();
        LogUtil.logSizeInfo("films", films.size());
        return films;
    }

    @Override
    public Optional<Film> getById(Long id) {
        LogUtil.logGetNotification("film", "id", id);
        Optional<Film> filmById = filmRepository.findById(id);
        LogUtil.logGetInfo("Film", "id", id, filmById.isPresent());
        return filmById;
    }

    @Override
    public List<Film> getAllFilmsToday() {
        LogUtil.logGetAllNotification("films today");
        List<Film> filmsToday = filmRepository.findAllByDateStartBeforeAndDateEndAfter(LocalDate.now(), LocalDate.now());
        LogUtil.logSizeInfo("films", filmsToday.size());
        return filmsToday;
    }
}
