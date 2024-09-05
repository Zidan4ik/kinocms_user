package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.Cinema;
import com.example.kinocms_user.entity.Hall;
import com.example.kinocms_user.entity.Mark;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.GalleriesType;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.model.CinemaDTO;
import com.example.kinocms_user.model.CinemasDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CinemaMapper {
    private static CinemasDTO toDTO(Cinema cinema) {
        CinemasDTO dto = new CinemasDTO();
        dto.setId(cinema.getId());
        dto.setNameLogo(cinema.getNameLogo());
        for (PageTranslation p : cinema.getPageTranslations()) {
            if (p.getLanguageCode().equals(LanguageCode.Ukr)) {
                dto.setNameCinema(p.getTitle());
            }
        }
        return dto;
    }

    public static List<CinemasDTO> toDTOList(List<Cinema> cinemas) {
        return cinemas.stream()
                .map(CinemaMapper::toDTO)
                .toList();
    }

    public static CinemaDTO toDTOCinema(Cinema cinema, PageTranslation translator, List<Hall> halls) {
        CinemaDTO dto = new CinemaDTO();
        dto.setId(cinema.getId());
        if (cinema.getMarksList() != null) {
            dto.setMarks(cinema.getMarksList().stream()
                    .map(Mark::getName)
                    .collect(Collectors.toSet()));
        }
        dto.setHallDTOS(HallMapper.toDTOList(halls));
        dto.setNameCinema(translator.getTitle());
        dto.setDescription(translator.getDescription());
        dto.setCondition(translator.getConditions());
        dto.setNameLogo(cinema.getNameLogo());
        dto.setNameBanner(cinema.getNameBanner());
        dto.setGalleryDTOS(GalleryMapper.toDTOList(cinema.getGalleries(),cinema.getId(), GalleriesType.cinemas));
        return dto;
    }
}
