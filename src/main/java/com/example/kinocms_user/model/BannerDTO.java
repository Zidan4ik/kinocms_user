package com.example.kinocms_user.model;

import com.example.kinocms_user.enums.BannerType;
import lombok.Data;

import java.util.List;

@Data
public class BannerDTO {
    private Long id;
    private Integer rotationSpeed;
    private Boolean status;
    private BannerType type;
    private List<BannerImageDTO> bannersImagesDTOS;
}
