package com.sanjeeban.WorkflowService.dto;



public class WorkflowRequestDto {

    private Long complaintId;
    private String userCode;
    private String requestType;
    private String remarks;

    private String residentUniqueId;

    public WorkflowRequestDto() {
    }

    public WorkflowRequestDto(Long complaintId, String userCode, String requestType, String remarks,String residentUniqueId) {
        this.complaintId = complaintId;
        this.userCode = userCode;
        this.requestType = requestType;
        this.remarks = remarks;
        this.residentUniqueId = residentUniqueId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getResidentUniqueId() {
        return residentUniqueId;
    }

    public void setResidentUniqueId(String residentUniqueId) {
        this.residentUniqueId = residentUniqueId;
    }
}
