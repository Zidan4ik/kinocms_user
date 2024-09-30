package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.User;
import com.example.kinocms_user.enums.LanguageCode;
import com.example.kinocms_user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImp userService;
    private List<User> loadedUsers;

    @BeforeEach
    public void setUp() {
        loadedUsers = loadUsers();
    }

    @Test
    void getByEmail() {
        String email = "roomich20031@gmail.com";
        User expectedUser = loadedUsers.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(expectedUser));

        Optional<User> userByEmail = userService.getByEmail(email);

        assertTrue(userByEmail.isPresent(), "User should be present");
        assertEquals(email, userByEmail.get().getEmail(), "Emails should match");

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void save() {
        User user = new User(1L, "Roman", "Pravnyk", "zinda", "123456789", "roomich20031@gmail.com", "New York", "password123", "1111222233334444", LanguageCode.Ukr, true, LocalDate.of(1990, 5, 15));
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.save(user);
        Optional<User> userById = userService.getById(1L);

        assertTrue(userById.isPresent(), "User should be present");
        assertEquals(user, userById.get(), "Users should match between");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getById() {
        Long id = 1L;
        User expectedUser = loadedUsers.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst()
                .orElse(null);

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(expectedUser));
        Optional<User> userById = userService.getById(id);

        assertTrue(userById.isPresent(), "User should be present");
        assertEquals(id, userById.get().getId(), "Id should match");
    }

    private List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "Roman", "Pravnyk", "zinda", "123456789", "roomich20031@gmail.com", "New York", "password123", "1111222233334444", LanguageCode.Ukr, true, LocalDate.of(1990, 5, 15)));
        users.add(new User(2L, "Jane", "Smith", "jsmith", "987654321", "romich20031@gmail.com", "Los Angeles", "password456", "2222333344445555", LanguageCode.Ukr, false, LocalDate.of(1985, 8, 22)));
        users.add(new User(3L, "Alex", "Johnson", "ajohnson", "456123789", "roman.pravnyk.TR.2021@lpnu.ua", "Chicago", "password789", "3333444455556666", LanguageCode.Eng, true, LocalDate.of(1995, 2, 10)));
        users.add(new User(4L, "Emily", "Brown", "ebrown", "321654987", "emily.brown@gmail.com", "Houston", "password101", "4444555566667777", LanguageCode.Eng, false, LocalDate.of(1992, 11, 30)));
        users.add(new User(5L, "Michael", "Davis", "mdavis", "789123456", "michael.davis@icloud.com", "Miami", "password202", "5555666677778888", LanguageCode.Eng, true, LocalDate.of(1988, 7, 5)));
        return users;
    }
}