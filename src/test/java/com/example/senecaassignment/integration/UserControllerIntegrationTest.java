package com.example.senecaassignment.integration;

import com.example.senecaassignment.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        mockMvc.perform(get("/users/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        User user = new User();
        user.setUsername("alice");
        user.setFullName("Alice Wonderland");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alice"))
                .andExpect(jsonPath("$.fullName").value("Alice Wonderland"));
    }

    @Test
    void shouldFetchCreatedUser() throws Exception {
        User user = new User();
        user.setUsername("bob");
        user.setFullName("Bob Builder");

        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User saved = objectMapper.readValue(response, User.class);

        mockMvc.perform(get("/users/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("bob"));
    }
}
