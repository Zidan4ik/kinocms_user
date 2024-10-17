package com.example.kinocms_user.controller;

import com.example.kinocms_user.model.UserDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthorityControllerTest {
    @InjectMocks
    private AuthorityController authorityController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorityController).build();
    }

    @Test
    void shouldReturnLoginView_WhenSuccessMessageIsNotPresent() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/login"));
        response.andExpectAll(
                status().isOk(),
                view().name("auth/login")
        ).andDo(print());
    }

    @Test
    void shouldReturnLoginView_WhenSuccessMessageIsPresent() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/login")
                .param("successMessage", "error"));
        response.andExpectAll(
                status().isOk(),
                view().name("auth/login"),
                model().attribute("successMessage", "error")
        ).andDo(print());
    }

    @Test
    void shouldReturnRegister_WhenRequestIsMade() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/register")
                .param("id", "1")
                .param("name", "JohnDoe123")
                .param("email", "johndoe@example.com")
                .param("password", "StrongPassword123!")
                .param("surname", "Doe")
                .param("nickname", "JDoe")
                .param("phone", "+1234567890")
                .param("birthday", "1990-01-01")
                .param("address", "123 Main St, Cityville")
                .param("card", "1234-5678-9012-3456")
                .param("isMan", "true"));
        response.andExpectAll(
                status().isOk(),
                model().attributeExists("userDTO"),
                model().attribute("userDTO", Matchers.hasProperty("id", Matchers.equalTo(1L)))
        ).andDo(print());
    }
}