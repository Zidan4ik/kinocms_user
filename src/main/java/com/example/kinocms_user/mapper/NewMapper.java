package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.Mark;
import com.example.kinocms_user.entity.New;
import com.example.kinocms_user.entity.PageTranslation;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.model.NewDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NewMapper {
    public static NewDTO toDTO(New new_){
        NewDTO dto = new NewDTO();
        dto.setId(new_.getId());
        dto.setMarks(new_.getMarksList().stream().map(Mark::getName).toList());
        dto.setNameImage(new_.getNameImage());
        dto.setNameImage(new_.getNameImage());
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDateParsed = LocalDate.parse(String.valueOf(new_.getDateOfCreation()), inputFormat);
        dto.setDateOfCreation(localDateParsed.format(outputFormat));
        for (PageTranslation p:new_.getPageTranslations()){
            if(p.getLanguageCode().equals(LanguageCode.Ukr)){
                dto.setDescription(p.getDescription());
                dto.setTitle(p.getTitle());
            }
        }
        return dto;
    }
    public static List<NewDTO> toDTOList(List<New> news){
        return news.stream()
                .map(NewMapper::toDTO)
                .toList();
    }
}
