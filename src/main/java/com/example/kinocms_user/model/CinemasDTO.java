package com.example.kinocms_user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemasDTO {
    private Long id;
    private String nameCinema;
    private String nameLogo;
    public String getPathToLogo(){
        return "/uploads/cinemas/logo/"+id+"/"+nameLogo;
    }
}
