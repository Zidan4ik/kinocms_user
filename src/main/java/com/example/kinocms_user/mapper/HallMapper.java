package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.Hall;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.GalleriesType;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.model.HallDTO;

import java.util.List;

public class HallMapper {
    public static HallDTO toDTO(Hall hall) {
        HallDTO dto = new HallDTO();
        dto.setId(hall.getId());
        dto.setNameBanner(hall.getNameBanner());
        dto.setNameSchema(hall.getNameSchema());
        dto.setNumber(hall.getNumber());
        dto.setGalleryDTOS(GalleryMapper.toDTOList(hall.getGalleryList(),hall.getId(), GalleriesType.halls));
        for (PageTranslation p: hall.getPageTranslations()){
            if(p.getLanguageCode().equals(LanguageCode.Ukr)){
                dto.setDescription(p.getDescription());
            }
        }
        return dto;
    }
    public static List<HallDTO> toDTOList(List<Hall> halls){
        return halls.stream()
                .map(HallMapper::toDTO)
                .toList();
    }
}
