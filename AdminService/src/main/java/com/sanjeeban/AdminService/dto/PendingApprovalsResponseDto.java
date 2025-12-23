package com.sanjeeban.AdminService.dto;

public class PendingApprovalsResponseDto {

    private long complaintId;
    private long workflowId;
    private String currentStatus;
    private Integer statusCode;
    private String lastUpdateBy;

    public PendingApprovalsResponseDto() {
    }

    public PendingApprovalsResponseDto(long complaintId, long workflowId, String currentStatus, Integer statusCode, String lastUpdateBy) {
        this.complaintId = complaintId;
        this.workflowId = workflowId;
        this.currentStatus = currentStatus;
        this.statusCode = statusCode;
        this.lastUpdateBy = lastUpdateBy;
    }

    public long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(long complaintId) {
        this.complaintId = complaintId;
    }

    public long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(long workflowId) {
        this.workflowId = workflowId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
