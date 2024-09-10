package com.example.kinocms_user.model;

import com.example.kinocms_user.enums.PageType;
import lombok.Data;

@Data
public class PageOfMainDTO {
    private Long id;
    private String phone1;
    private String phone2;
    private Boolean status;
    private PageType type;
}
