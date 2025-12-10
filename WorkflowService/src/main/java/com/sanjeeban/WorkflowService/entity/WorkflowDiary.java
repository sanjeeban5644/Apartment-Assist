package com.sanjeeban.WorkflowService.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "t_workflow_diary",
        schema="facility_workorder"
)
public class WorkflowDiary {

    @Id
    @Column(name="workflow_status_id")
    private Long workflowStatusId;

    @Column(name="status")
    private String status;

    @Column(name="sub_status")
    private String subStatus;

    @Column(name="updated_by")
    private String updatedBy;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="remarks")
    private String remarks;

    public WorkflowDiary(Long workflowStatusId, String status, String subStatus, String updatedBy, LocalDateTime updatedAt, String remarks) {
        this.workflowStatusId = workflowStatusId;
        this.status = status;
        this.subStatus = subStatus;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.remarks = remarks;
    }

    public Long getWorkflowStatusId() {
        return workflowStatusId;
    }

    public void setWorkflowStatusId(Long workflowStatusId) {
        this.workflowStatusId = workflowStatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

