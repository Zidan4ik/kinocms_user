package com.example.kinocms_user.model;

import lombok.Data;

@Data
public class ContactDTO {
    private Long id;
    private String title;
    private String address;
    private String width;
    private String length;
    private String nameLogo;
    public String getPathToLogo() {
        return "/uploads/contacts/logo/" + id + "/" + nameLogo;
    }

}
