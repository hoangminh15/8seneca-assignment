package com.example.senecaassignment.models;

import jakarta.persistence.Entity;

@Entity
public class Bug extends Task {
    private String severity;
    private String stepsToReproduce;

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStepsToReproduce() {
        return stepsToReproduce;
    }

    public void setStepsToReproduce(String stepsToReproduce) {
        this.stepsToReproduce = stepsToReproduce;
    }
}
