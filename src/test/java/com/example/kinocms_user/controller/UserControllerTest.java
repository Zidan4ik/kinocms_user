package com.example.kinocms_user.controller;

import com.example.kinocms_user.auth.UserDetailsImp;
import com.example.kinocms_user.entity.User;
import com.example.kinocms_user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.mockito.Mockito.when;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private User user;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User();
        user.setId(1L);
        user.setName("JohnDoe");
        user.setEmail("johndoe@example.com");
        user.setPassword("$2a$10$VLi/X7EzXdZBF3NZ6CSnRO6BzfZkQ6uQ62sgv0uR7crmvFBUis31y");
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturnUserProfileData() throws Exception {

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        when(userService.getById(user.getId())).thenReturn(Optional.of(user));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImp(user));

        ResultActions response = mockMvc.perform(get("/user/profile/data"));
        response.andExpectAll(
                        status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andDo(print());
    }

    @Test
    void shouldReturnNull_WhenAuthenticationIsNotAnonymousAuthenticationToken() throws Exception {
        Authentication anonymousAuth = new AnonymousAuthenticationToken("key", "anonymousUser",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(anonymousAuth);

        ResultActions response = mockMvc.perform(get("/user/profile/data"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print());
    }

    @Test
    void shouldReturnNull_WhenUserNotFound() throws Exception {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        when(userService.getById(user.getId())).thenReturn(Optional.empty());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImp(user));

        ResultActions response = mockMvc.perform(get("/user/profile/data"));
        response.andExpectAll(
                        status().isOk(),
                        jsonPath("$").doesNotExist())
                .andDo(print());
    }

    @Test
    void shouldReturnSavedUser_WhenDataIsValid() throws Exception {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword123");
        mockMvc.perform(post("/user/add")
                        .param("name", "Test User")
                        .param("email", "test@example.com")
                        .param("password", "password123")
                        .param("isMan", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.flash().attribute("successMessage", "User was successfully created. Please log in."));
    }

    @Test
    void shouldReturnModelView_WhenEmailWasExist() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@gmail.com");

        when(userService.getByEmail(user.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/user/add")
                        .param("name", "test user")
                        .param("email", "user@gmail.com")
                        .param("password", "password123")
                        .param("isMan", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"));
    }

    @Test
    void shouldReturnModelView_WithValidationErrors() throws Exception {
        ResultActions response = mockMvc.perform(post("/user/add")
                .param("name", "")
                .param("email", "invalid-email")
                .param("password", "123"));

        response.andExpectAll(
                status().isOk(),
                view().name("auth/register"),
                model().attributeHasFieldErrors("user", "name", "email", "password")
        ).andDo(print());
    }

    @Test
    void shouldUpdateUser_WhenPasswordIsNull() throws Exception {
        User user1 = new User();
        user1.setId(2L);
        user1.setName("user");
        user1.setEmail("user@gmail.com");
        when(userService.getById(user1.getId())).thenReturn(Optional.of(user1));
        mockMvc.perform(post("/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateUser_WhenPasswordIsPresent() throws Exception {
        User user1 = new User();
        user1.setId(2L);
        user1.setName("user");
        user1.setEmail("user@gmail.com");
        user1.setPassword("$2a$10$VLi/X7EzXdZBF3NZ6CSnRO6BzfZkQ6uQ62sgv0uR7crmvFBUis31y");
        when(passwordEncoder.encode(user1.getPassword())).thenReturn("$2a$10$VLi/X7EzXdZBF3NZ6CSnRO6BzfZkQ6uQ62sgv0uR7crmvFBUis31y");
        mockMvc.perform(post("/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk());
    }
    @Test
    void shouldReturnProfileView_WhenRequestIsMade() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/profile"));
        response.andExpectAll(
                status().isOk(),
                view().name("pages/profile")
        ).andDo(print());
    }
}