package com.sanjeeban.AdminService.dto;


public class ManagerDetailsDto {

    private String managerId;
    private String role;
    private String primaryBlock;


    public ManagerDetailsDto(String managerId, String role, String primaryBlock) {
        this.managerId = managerId;
        this.role = role;
        this.primaryBlock = primaryBlock;
    }

    public ManagerDetailsDto() {

    }

    public String  getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPrimaryBlock() {
        return primaryBlock;
    }

    public void setPrimaryBlock(String primaryBlock) {
        this.primaryBlock = primaryBlock;
    }
}
