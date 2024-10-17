package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.CeoBlock;
import com.example.kinocms_user.entity.Page;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.GalleriesType;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.model.PageDTO;
import com.example.kinocms_user.model.PageMenuDTO;
import com.example.kinocms_user.model.PageOfMainDTO;

import java.util.List;

public class PageMapper {
    public static PageDTO toDTO(Page page) {
        PageDTO dto = new PageDTO();
        dto.setId(page.getId());
        dto.setStatus(page.isStatus());
        dto.setType(page.getType().toString());
        dto.setNameBanner(page.getNameBanner());
        dto.setNameImage1(page.getNameImage1());
        dto.setNameImage2(page.getNameImage2());
        dto.setNameImage3(page.getNameImage3());
        dto.setGalleryDTOS(GalleryMapper.toDTOList(page.getGalleries(), page.getId(), GalleriesType.pages));
        for (PageTranslation translator : page.getPageTranslations()) {
            if (translator.getLanguageCode().equals(LanguageCode.Ukr)) {
                dto.setTitle(translator.getTitle());
                dto.setDescription(translator.getDescription());
            }
        }
        return dto;
    }

    private static PageMenuDTO toDTOMenu(Page page) {
        PageMenuDTO dto = new PageMenuDTO();
        dto.setId(page.getId());
        dto.setType(page.getType());
        for (PageTranslation p : page.getPageTranslations()) {
            if (p.getLanguageCode().equals(LanguageCode.Ukr)) {
                dto.setTitle(p.getTitle());
            }
        }
        return dto;
    }

    public static List<PageMenuDTO> toDTOMenuList(List<Page> pages) {
        return pages.stream()
                .map(PageMapper::toDTOMenu)
                .toList();
    }

    public static PageOfMainDTO toDTOMain(Page page) {
        PageOfMainDTO dto = new PageOfMainDTO();
        dto.setId(page.getId());
        dto.setPhone1(page.getPhoneFirst());
        dto.setPhone2(page.getPhoneSecond());
        dto.setType(page.getType());
        dto.setStatus(page.isStatus());
        for(CeoBlock c:page.getCeoBlocks()){
            if(c.getLanguageCode().equals(LanguageCode.Ukr)){
                dto.setSeoText(c.getSeoText());
            }
        }
        return dto;
    }
}
