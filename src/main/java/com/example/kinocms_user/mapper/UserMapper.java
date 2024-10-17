package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.User;
import com.example.kinocms_user.model.UserDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserMapper {
    public static UserDTO toDTO(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getLastName());
        dto.setNickname(entity.getNickname());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setCard(entity.getNumberCard());
        dto.setIsMan(entity.isMan());
        if (entity.getDateOfBirthday() != null) {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate dateInput = LocalDate.parse(entity.getDateOfBirthday().toString(), inputFormat);
            dto.setBirthday(dateInput.format(outputFormat));
        }
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setLastName(dto.getSurname());
        entity.setNickname(dto.getNickname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setNumberCard(dto.getCard());
        entity.setPassword(dto.getPassword());
        entity.setRoles("ROLE_USER");
        if(dto.getIsMan() != null){
            entity.setMan(dto.getIsMan());
        }
        if(dto.getBirthday() != null && !dto.getBirthday().isEmpty()){
            entity.setDateOfBirthday(LocalDate.parse(dto.getBirthday(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        return entity;
    }
}
