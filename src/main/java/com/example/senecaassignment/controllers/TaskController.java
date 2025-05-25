package com.example.senecaassignment.controllers;

import com.example.senecaassignment.dtos.TaskRequestDto;
import com.example.senecaassignment.dtos.TaskResponseDto;
import com.example.senecaassignment.models.Status;
import com.example.senecaassignment.models.Task;
import com.example.senecaassignment.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto dto) {
        Task task = taskService.createTask(dto);
        return ResponseEntity.ok(taskService.toDto(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable Long id) {
        Task task = taskService.getTask(id);
        return ResponseEntity.ok(taskService.toDto(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> listTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Long userId
    ) {
        List<Task> tasks = taskService.getTasksFiltered(status, userId);
        return ResponseEntity.ok(tasks.stream().map(taskService::toDto).toList());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDto>> searchTasks(@RequestParam String query) {
        List<Task> result = taskService.searchTasks(query);
        return ResponseEntity.ok(result.stream().map(taskService::toDto).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id, @RequestBody TaskRequestDto dto) {
        Task task = taskService.updateTask(id, dto);
        return ResponseEntity.ok(taskService.toDto(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
