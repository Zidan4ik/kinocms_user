package com.example.kinocms_user.model;

import com.example.kinocms_user.enums.GalleriesType;
import com.example.kinocms_user.enums.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GalleryDTO {
    private Long id;
    private String nameImage;
    private GalleriesType galleriesType;
    private ImageType imageType;
    private String pathToImage;
}
