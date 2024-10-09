package com.example.kinocms_user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {
    private Long id;
    private String title;
    private String description;
    private String nameImage;
    private String dateStart;
    private String dateEnd;
    private List<String> marks;
    private List<String> genres;
    private List<GalleryDTO> galleries;
    private String year;
    private String budget;
    private String time;
    private String trailer;
    public String getPathToImage() {
        return "/kinocms_user/uploads/films/main-image/" + id + "/" + nameImage;
    }
}

