package com.sanjeeban.WorkflowService.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "t_workflow_status",
        schema="facility_workorder"
)
public class WorkflowStatus {

    @Id
    @Column(name = "complaint_id", nullable = false)
    private Long complaintId;

    @Column(name="workflow_status_id")
    private Long workflowStatusId;

    @Column(name="current_status",nullable = false)
    private String currentStatus;

    @Column(name="sub_status",nullable = true)
    private String substatus;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();


    public WorkflowStatus() {
    }

    public WorkflowStatus(Long complaintId, Long workflowStatusId, String currentStatus, String substatus, String updatedBy, String remarks, LocalDateTime updatedAt) {
        this.complaintId = complaintId;
        this.workflowStatusId = workflowStatusId;
        this.currentStatus = currentStatus;
        this.substatus = substatus;
        this.updatedBy = updatedBy;
        this.remarks = remarks;
        this.updatedAt = updatedAt;
    }

    public Long getWorkflowStatusId() {
        return workflowStatusId;
    }

    public void setWorkflowStatusId(Long workflowStatusId) {
        this.workflowStatusId = workflowStatusId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSubstatus() {
        return substatus;
    }

    public void setSubstatus(String substatus) {
        this.substatus = substatus;
    }
}
