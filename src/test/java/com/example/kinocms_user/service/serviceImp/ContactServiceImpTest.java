package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.Contact;
import com.example.kinocms_user.entity.Page;
import com.example.kinocms_user.repository.ContactRepository;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceImpTest {
    @Mock
    private ContactRepository contactRepository;
    @InjectMocks
    private ContactServiceImp contactService;
    private List<Contact> loadedContacts;

    @BeforeEach
    void setUp() {
        loadedContacts = loadContact();
    }

    @Test
    void getAll() {
        Mockito.when(contactRepository.findAll()).thenReturn(loadedContacts);
        List<Contact> contacts = contactService.getAll();
        assertNotNull(contacts,"Collection should not be null");
        assertEquals(loadedContacts.size(),contacts.size(),"Sizes list of shares should be match");
    }

    private List<Contact> loadContact() {
        Contact contact1 = new Contact(1L, "John Doe", "123 Main Street, Springfield",
                "45.4215,-75.6972", "john_logo.png", new Page()
        );

        Contact contact2 = new Contact(
                2L, "Jane Smith", "456 Elm Street, Gotham",
                "40.7128,-74.0060", "jane_logo.png", new Page()
        );

        Contact contact3 = new Contact(
                3L, "Acme Corp", "789 Oak Street, Metropolis",
                "37.7749,-122.4194", "acme_logo.png", new Page()
        );
        return List.of(contact1, contact2, contact3);
    }
}