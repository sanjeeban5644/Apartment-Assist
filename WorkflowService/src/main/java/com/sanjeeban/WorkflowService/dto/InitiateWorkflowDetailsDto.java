package com.sanjeeban.WorkflowService.dto;



public class InitiateWorkflowDetailsDto {

    private String complaintId;
    private String description;
    private String flag;

    public InitiateWorkflowDetailsDto(String complaintId, String description, String flag) {
        this.complaintId = complaintId;
        this.description = description;
        this.flag = flag;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
