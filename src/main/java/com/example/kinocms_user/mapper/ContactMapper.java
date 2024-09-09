package com.example.kinocms_user.mapper;

import com.example.kinocms_user.entity.Contact;
import com.example.kinocms_user.model.ContactDTO;

import java.util.List;

public class ContactMapper {
    private static ContactDTO toDTO(Contact contact){
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setTitle(contact.getName());
        dto.setAddress(contact.getAddress());
        dto.setNameLogo(contact.getNameLogo());
        String[] coordinates = contact.getCoordinates().split(",");
        if(coordinates.length == 2){
            dto.setLength(coordinates[0]);
            dto.setWidth(coordinates[1]);
        }
        return dto;
    }
    public static List<ContactDTO> toDTOList(List<Contact> contacts){
        return contacts.stream()
                .map(ContactMapper::toDTO)
                .toList();
    }
}
