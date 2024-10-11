package com.example.kinocms_user.model;

import lombok.Data;

@Data
public class BannerImageDTO {
    private Long id;
    private String nameImage;
    private String text;
    private String url;
    public String getPathToImage() {
        return "/kinocms/uploads/banner/" + id + "/" + nameImage;
    }
}
