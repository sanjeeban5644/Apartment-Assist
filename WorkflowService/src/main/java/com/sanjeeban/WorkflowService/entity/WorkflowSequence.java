package com.sanjeeban.WorkflowService.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(
        name="t_workflow_sequence",
        schema="facility_workorder"
)
public class WorkflowSequence {

    @Id
    @Column(name="sequence_date")
    private LocalDate date;

    @Column(name="last_sequence")
    private int lastSequence;


    public WorkflowSequence() {
    }

    public WorkflowSequence(LocalDate date, int lastSequence) {
        this.date = date;
        this.lastSequence = lastSequence;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getLastSequence() {
        return lastSequence;
    }

    public void setLastSequence(int lastSequence) {
        this.lastSequence = lastSequence;
    }
}
