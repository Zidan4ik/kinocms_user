package com.example.kinocms_user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private Long id;
    private String title;
    private String type;
    private String nameImage1;
    private String nameImage2;
    private String nameImage3;
    private String nameBanner;
    private String description;
    private boolean status;
    private List<GalleryDTO> galleryDTOS;

    public String getPathToBanner() {
        return "/uploads/pages/banner/" + id + "/" + nameBanner;
    }

    public String getPathToImage1() {
        return "/uploads/pages/image1/" + id + "/" + nameImage1;
    }
    public String getPathToImage2() {
        return "/uploads/pages/image2/" + id + "/" + nameImage2;
    }
    public String getPathToImage3() {
        return "/uploads/pages/image3/" + id + "/" + nameImage3;
    }
}
