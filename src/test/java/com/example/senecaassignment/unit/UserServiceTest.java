package com.example.senecaassignment.unit;

import com.example.senecaassignment.models.User;
import com.example.senecaassignment.repositories.UserRepository;
import com.example.senecaassignment.services.UserService;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        User input = new User();
        input.setUsername("john");
        input.setFullName("John Doe");

        User saved = new User();
        saved.setId(1L);
        saved.setUsername("john");
        saved.setFullName("John Doe");

        when(userRepository.save(input)).thenReturn(saved);

        User result = userService.createUser(input);

        assertEquals(1L, result.getId());
        assertEquals("john", result.getUsername());
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void shouldReturnUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("alice");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUser(1L);

        assertEquals("alice", result.getUsername());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(42L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            userService.getUser(42L);
        });

        assertTrue(thrown.getMessage().contains("User not found"));
    }

    @Test
    void shouldUpdateUserFullName() {
        User existing = new User();
        existing.setId(1L);
        existing.setUsername("emma");
        existing.setFullName("Emma Old");

        User update = new User();
        update.setFullName("Emma New");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(1L, update);

        assertEquals("Emma New", result.getFullName());
    }

    @Test
    void shouldDeleteUserById() {
        userService.deleteUser(99L);
        verify(userRepository, times(1)).deleteById(99L);
    }

    @Test
    void shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(new User(), new User()));
        assertEquals(2, userService.getAllUsers().size());
    }
}
