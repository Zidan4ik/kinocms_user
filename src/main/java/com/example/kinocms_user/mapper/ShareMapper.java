package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.Mark;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.entity.Share;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.model.ShareDTO;
import com.example.kinocms_user.model.SharesDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShareMapper {
    public static SharesDTO toDTO(Share share) {
        SharesDTO dto = new SharesDTO();
        dto.setId(share.getId());
        dto.setMarks(share.getMarksList().stream().map(Mark::getName).toList());

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate inputDate = LocalDate.parse(share.getDateOfCreation().toString(), inputFormat);
        dto.setDateOfCreation(inputDate.format(outputFormat));

        dto.setNameImage(share.getNameImage());
        for (PageTranslation p:share.getPageTranslations()){
            if(p.getLanguageCode().equals(LanguageCode.Ukr)){
                dto.setDescription(p.getDescription());
                dto.setTitle(p.getTitle());
            }
        }
        return dto;
    }
    public static ShareDTO toDTOShare(Share share) {
        ShareDTO dto = new ShareDTO();
        dto.setId(share.getId());
        dto.setMarks(share.getMarksList().stream().map(Mark::getName).toList());
        dto.setNameImage(share.getNameImage());
        dto.setNameBanner(share.getNameBanner());
        for (PageTranslation p:share.getPageTranslations()){
            if(p.getLanguageCode().equals(LanguageCode.Ukr)){
                dto.setDescription(p.getDescription());
                dto.setTitle(p.getTitle());
            }
        }
        return dto;
    }
    public static List<SharesDTO> toDTOList(List<Share> shares){
        return shares.stream()
                .map(ShareMapper::toDTO)
                .toList();
    }
}
