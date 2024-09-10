package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.Banner;
import com.example.kinocms_user.entity.BannerImage;
import com.example.kinocms_user.model.BannerDTO;
import com.example.kinocms_user.model.BannerImageDTO;

import java.util.List;

public class BannerMapper {
    private static BannerImageDTO toDTO(BannerImage entity){
        BannerImageDTO dto = new BannerImageDTO();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setUrl(entity.getUrl());
        dto.setNameImage(entity.getNameImage());
        return dto;
    }
    private static List<BannerImageDTO> toDTOListBannersImages(List<BannerImage> bannerImages){
        return bannerImages.stream()
                .map(BannerMapper::toDTO)
                .toList();
    }
    private static BannerDTO toDTO(Banner banner){
        BannerDTO dto = new BannerDTO();
        dto.setId(banner.getId());
        dto.setType(banner.getType());
        dto.setStatus(banner.isStatus());
        dto.setRotationSpeed(banner.getRotationSpeed());
        dto.setBannersImagesDTOS(toDTOListBannersImages(banner.getBannerImage()));
        return dto;
    }
    public static List<BannerDTO> toDTOListBanner(List<Banner> banners){
        return banners.stream()
                .map(BannerMapper::toDTO)
                .toList();
    }
}
