package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.*;
import com.example.kinocms_user.enums.GalleriesType;
import com.example.kinocms_user.enums.ImageType;
import com.example.kinocms_user.model.FilmDTO;
import com.example.kinocms_user.model.GalleryDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FilmMapper {
    public static FilmDTO toDTO(Film film, PageTranslation translator, List<Mark> marks) {
        FilmDTO dto = new FilmDTO();
        dto.setId(film.getId());
        dto.setTitle(translator.getTitle());
        dto.setDescription(translator.getDescription());
        LocalDate inputDateStart = LocalDate.parse(String.valueOf(film.getDateStart()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate inputDateEnd = LocalDate.parse(String.valueOf(film.getDateEnd()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dto.setDateStart(inputDateStart.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        dto.setDateEnd(inputDateEnd.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        dto.setNameImage(film.getNameImage());
        dto.setBudget(String.valueOf(film.getBudget()));
        dto.setYear(String.valueOf(film.getYear()));
        dto.setTime(film.getDurationTime().toString());
        dto.setTrailer(film.getLinkTrailer());
        dto.setGenres(film.getGenresList().stream()
                .map(Genre::getName)
                .toList());
        if (marks != null) {
            dto.setMarks(marks.stream()
                    .map(Mark::getName)
                    .toList());
        }
        dto.setGalleries(GalleryMapper.toDTOList(film.getGalleries(), film.getId(),GalleriesType.films));
        return dto;
    }
}
