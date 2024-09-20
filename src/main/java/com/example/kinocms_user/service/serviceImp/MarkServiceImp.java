package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.Mark;
import com.example.kinocms_user.repository.MarkRepository;
import com.example.kinocms_user.service.MarkService;
import com.example.kinocms_user.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImp implements MarkService {
    private final MarkRepository markRepository;
    @Override
    public List<Mark> getAll() {
        LogUtil.logGetAllNotification("marks");
        List<Mark> marks = markRepository.findAll();
        LogUtil.logSizeInfo("marks",marks.size());
        return marks;
    }

    @Override
    public List<Mark> getAllByFilms(List<Film> films) {
        LogUtil.logGetAllNotification("marks","films",films);
        List<Long> ids = films.stream()
                .map(Film::getId)
                .toList();
        List<Mark> marksByFilms = markRepository.getAllByFilms(ids);
        LogUtil.logSizeInfo("marks by films", marksByFilms.size());
        return marksByFilms;
    }
}
