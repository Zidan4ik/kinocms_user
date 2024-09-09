package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Contact;
import com.example.kinocms_user.repository.ContactRepository;
import com.example.kinocms_user.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImp implements ContactService {
    private final ContactRepository contactRepository;
    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }
}
