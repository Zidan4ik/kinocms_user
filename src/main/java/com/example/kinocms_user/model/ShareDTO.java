package com.example.kinocms_user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareDTO {
    private Long id;
    private String title;
    private String nameImage;
    private String nameBanner;
    private String description;
    private List<String> marks;
    public String getPathToImage() {
        return "/kinocms_user/uploads/shares/image/" + id + "/" + nameImage;
    }
    public String getPathToBanner() {
        return "/kinocms_user/uploads/shares/banner/" + id + "/" + nameBanner;
    }
}
