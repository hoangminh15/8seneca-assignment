package com.example.senecaassignment.dtos;

import com.example.senecaassignment.models.Status;

import java.time.LocalDateTime;

public class TaskResponseDto {
    public Long id;
    public String name;
    public LocalDateTime createdAt;
    public Status status;
    public String description;

    // "BUG" or "FEATURE"
    public String type;
    public Long userId;

    // Optional Bug fields
    public String severity;
    public String stepsToReproduce;

    // Optional Feature fields
    public Integer businessValue;
    public String deadline;
}
