package com.example.kinocms_user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallDTO {
    private Long id;
    private Integer number;
    private String description;
    private List<GalleryDTO> galleryDTOS;
    private String nameSchema;
    private String nameBanner;

    public String getPathToSchema() {
        return "/uploads/halls/schema/" + id + "/" + nameSchema;
    }

    public String getPathToBanner() {
        return "/uploads/halls/banner/" + id + "/" + nameBanner;
    }
}
