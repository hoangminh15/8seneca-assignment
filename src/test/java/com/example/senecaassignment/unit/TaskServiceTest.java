package com.example.senecaassignment.unit;

import com.example.senecaassignment.dtos.TaskRequestDto;
import com.example.senecaassignment.models.*;
import com.example.senecaassignment.repositories.TaskRepository;
import com.example.senecaassignment.repositories.UserRepository;

import com.example.senecaassignment.services.TaskService;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateBugTaskSuccessfully() {
        TaskRequestDto dto = new TaskRequestDto();
        dto.name = "Fix bug";
        dto.type = "BUG";
        dto.userId = 1L;
        dto.status = Status.OPEN;
        dto.description = "Short description";
        dto.severity = "HIGH";
        dto.stepsToReproduce = "Step 1";

        User mockUser = new User();
        mockUser.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task result = taskService.createTask(dto);

        assertInstanceOf(Bug.class, result);
        assertEquals("HIGH", ((Bug) result).getSeverity());
    }

    @Test
    void shouldThrowIfInvalidTaskType() {
        TaskRequestDto dto = new TaskRequestDto();
        dto.type = "OTHER";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(dto);
        });

        assertTrue(ex.getMessage().contains("Invalid task type"));
    }
}
