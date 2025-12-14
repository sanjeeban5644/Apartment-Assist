package com.sanjeeban.ResidentService.dto;

public class LodgeComplaintDto {

    private String requestType;
    private String userCode;
    private String remarks;

    public LodgeComplaintDto() {
    }

    public LodgeComplaintDto(String requestType, String userCode, String remarks) {
        this.requestType = requestType;
        this.userCode = userCode;
        this.remarks = remarks;
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
}
