package com.example.kinocms_user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewDTO {
    private Long id;
    private String title;
    private String nameImage;
    private String description;
    private String dateOfCreation;
    private List<String> marks;
    public String getPathToImage() {
        return "/uploads/news/image/" + id + "/" + nameImage;
    }
}
