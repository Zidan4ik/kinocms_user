package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.Gallery;
import com.example.kinocms_user.enums.GalleriesType;
import com.example.kinocms_user.enums.ImageType;
import com.example.kinocms_user.model.GalleryDTO;

import java.util.Collections;
import java.util.List;

public class GalleryMapper {
    private static GalleryDTO toDTO(Gallery gallery, Long id, GalleriesType galleriesType) {
        GalleryDTO dto = new GalleryDTO();
        dto.setId(gallery.getId());
        dto.setNameImage(gallery.getLinkImage());
        dto.setImageType(ImageType.galleries);
        dto.setGalleriesType(GalleriesType.films);
        dto.setPathToImage("/kinocms/uploads/" + galleriesType + "/" + ImageType.galleries + "/" + id + "/" + gallery.getLinkImage());
        return dto;
    }

    public static List<GalleryDTO> toDTOList(List<Gallery> galleries, Long id, GalleriesType galleriesType) {
        if (galleries == null) {
            return Collections.emptyList();
        }
        return galleries.stream()
                .map(gallery -> toDTO(gallery, id, galleriesType))
                .toList();
    }
}
