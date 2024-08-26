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
    private String nameImage;
    private String dateStart;
    private String dateEnd;
    private List<String> marks;

    public String getPathToImage() {
        return "/uploads/films/main-image/" + id + "/" + nameImage;
    }
}

