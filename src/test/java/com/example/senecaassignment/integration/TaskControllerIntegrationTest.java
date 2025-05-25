package com.example.senecaassignment.integration;

import com.example.senecaassignment.dtos.TaskRequestDto;
import com.example.senecaassignment.models.Status;

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
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn404WhenTaskNotFound() throws Exception {
        mockMvc.perform(get("/tasks/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found"));
    }

    @Test
    void shouldCreateBugTask() throws Exception {
        TaskRequestDto dto = new TaskRequestDto();
        dto.name = "Fix auth issue";
        dto.type = "BUG";
        dto.status = Status.OPEN;
        dto.description = "Short description";
        dto.userId = 1L;
        dto.severity = "HIGH";
        dto.stepsToReproduce = "Try login with invalid data";

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fix auth issue"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }
}
