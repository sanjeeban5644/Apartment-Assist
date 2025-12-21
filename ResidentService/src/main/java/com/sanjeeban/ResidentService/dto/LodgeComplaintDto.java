package com.sanjeeban.ResidentService.dto;

public class LodgeComplaintDto {

    private String requestType;
    private String userCode;
    private String remarks;
    private String residentUniqueId;

    private String complaintId;



    public LodgeComplaintDto() {
    }

    public LodgeComplaintDto(String requestType, String userCode, String remarks,String residentUniqueId,String complaintId) {
        this.requestType = requestType;
        this.userCode = userCode;
        this.remarks = remarks;
        this.residentUniqueId = residentUniqueId;
        this.complaintId = complaintId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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


    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }
}
