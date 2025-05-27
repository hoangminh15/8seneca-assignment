package com.example.senecaassignment.services;

import com.example.senecaassignment.dtos.TaskRequestDto;
import com.example.senecaassignment.dtos.TaskResponseDto;
import com.example.senecaassignment.models.*;
import com.example.senecaassignment.repositories.TaskRepository;
import com.example.senecaassignment.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TaskService {

    private final String BUG = "BUG";
    private final String FEATURE = "FEATURE";

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task createTask(TaskRequestDto dto) {
        validateDto(dto);
        User user = userRepository.findById(dto.userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Task task = mapDtoToEntity(dto);
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    public List<Task> getTasksFiltered(Status status, Long userId) {
        return taskRepository.findByStatusAndUser(status, userId);
    }

    public List<Task> searchTasks(String text) {
        return taskRepository.searchByText(text);
    }

    public Task updateTask(Long id, TaskRequestDto dto) {
        Task existing = getTask(id);
        Task updated = mapDtoToEntity(dto);
        updated.setId(existing.getId());
        updated.setUser(existing.getUser());
        updated.setCreatedAt(existing.getCreatedAt());
        return taskRepository.save(updated);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private void validateDto(TaskRequestDto dto) {
        if (dto.type == null || (!dto.type.equalsIgnoreCase(BUG) && !dto.type.equalsIgnoreCase(FEATURE))) {
            throw new IllegalArgumentException("Invalid task type: " + dto.type);
        }
    }

    private Task mapDtoToEntity(TaskRequestDto dto) {
        switch (dto.type.toUpperCase()) {
            case BUG:
                Bug bug = new Bug();
                bug.setSeverity(dto.severity);
                bug.setStepsToReproduce(dto.stepsToReproduce);
                bug.setName(dto.name);
                bug.setStatus(dto.status);
                bug.setDescription(dto.description);
                return bug;
            case FEATURE:
                Feature feature = new Feature();
                feature.setBusinessValue(dto.businessValue);
                feature.setDeadline(dto.deadline);
                feature.setName(dto.name);
                feature.setStatus(dto.status);
                feature.setDescription(dto.description);
                return feature;
            default:
                throw new IllegalArgumentException("Invalid task type: " + dto.type);
        }
    }

    public TaskResponseDto toDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.id = task.getId();
        dto.name = task.getName();
        dto.status = task.getStatus();
        dto.createdAt = task.getCreatedAt();
        dto.userId = task.getUser().getId();
        dto.description = task.getDescription();

        if (task instanceof Bug bug) {
            dto.type = BUG;
            dto.severity = bug.getSeverity();
            dto.stepsToReproduce = bug.getStepsToReproduce();
        } else if (task instanceof Feature feature) {
            dto.type = FEATURE;
            dto.businessValue = feature.getBusinessValue();
            dto.deadline = feature.getDeadline() != null ? feature.getDeadline().toString() : null;
        }

        return dto;
    }

}
