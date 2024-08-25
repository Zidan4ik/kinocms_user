package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.Film;
import com.example.kinocms_user.entity.Mark;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.model.FilmDTO;
import org.springframework.cglib.core.Local;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FilmMapper {
    public static FilmDTO toDTO(Film film, PageTranslation translator,
                         List<Mark> marks){
        FilmDTO dto = new FilmDTO();
        dto.setId(film.getId());
        dto.setTitle(translator.getTitle());
        LocalDate inputDateStart = LocalDate.parse(String.valueOf(film.getDateStart()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate inputDateEnd = LocalDate.parse(String.valueOf(film.getDateEnd()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dto.setDateStart(inputDateStart.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        dto.setDateEnd(inputDateEnd.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        dto.setMarks(marks);
        dto.setNameImage(film.getNameImage());
        return dto;
    }

//    public List<FilmDTO> toDTOList(List<Film> films){
//        return films.stream()
//                .map(FilmMapper::toDTO)
//                .toList();
//    }
}
