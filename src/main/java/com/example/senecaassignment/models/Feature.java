package com.example.senecaassignment.models;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Feature extends Task {
    private Integer businessValue;
    private LocalDate deadline;

    public Integer getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(Integer businessValue) {
        this.businessValue = businessValue;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}

