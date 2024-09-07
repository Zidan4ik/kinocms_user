package com.example.kinocms_user.model;

import com.example.kinocms_user.enums.PageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageMenuDTO {
    private Long id;
    private String title;
    private PageType type;
}
