package com.example.senecaassignment.dtos;

import com.example.senecaassignment.models.Status;

import java.time.LocalDate;

public class TaskRequestDto {
    public String name;

    // BUG or FEATURE
    public String type;
    public Long userId;
    public Status status;
    public String description;

    // Bug fields
    public String severity;
    public String stepsToReproduce;

    // Feature fields
    public Integer businessValue;
    public LocalDate deadline;
}
