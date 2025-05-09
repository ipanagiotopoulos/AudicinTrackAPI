package com.audicin.backend.track.api.controllers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.audicin.backend.track.api.security.dtos.request.RegisterUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRegisterUser() throws Exception {
        RegisterUserDto userDto =
                RegisterUserDto.builder().email("jp@gmail.com").fullName("User")
                        .password("password").build();

        mockMvc.perform(
                        post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(userDto)))
                .andExpect(status().isOk()).andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.email").value(
                        "partner@email.com")).andExpect(
                        (ResultMatcher) jsonPath("$.fullName").value(
                                "partner@email.com")); // Adjust expected
        // response
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
