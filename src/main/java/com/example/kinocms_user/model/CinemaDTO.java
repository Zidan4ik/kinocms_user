package com.example.kinocms_user.model;

import com.example.kinocms_user.entity.Hall;
import com.example.kinocms_user.entity.Mark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDTO {
    private Long id;
    private String nameLogo;
    private String nameBanner;
    private String nameCinema;
    private List<Hall> halls;
    private String description;
    private String condition;
    private Set<Mark> marks;
    private List<GalleryDTO> galleryDTOS;

    public String getPathToLogo() {
        return "/uploads/cinemas/logo/" + id + "/" + nameLogo;
    }

    public String getPathToBanner() {
        return "/uploads/cinemas/banner/" + id + "/" + nameBanner;
    }

}
