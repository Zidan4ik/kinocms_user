package com.example.kinocms_user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = "Name should not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я. _-]{4,15}$", message = "Uncorrected nickname")
    private String name;
    @NotBlank(message = "Email should not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Uncorrected email")
    private String email;
    @NotBlank(message = "Password should not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9а-яА-Я.,:; _?!+=/'\\\\\"*(){}\\[\\]\\-]{4,100}$", message = "Uncorrected password")
    private String password;
    private String surname;
    private String nickname;
    private String phone;
    private String birthday;
    private String address;
    private String card;
    private Boolean isMan;
    private String city;
}
