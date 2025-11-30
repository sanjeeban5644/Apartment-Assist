package com.sanjeeban.AdminService.dto;


public class ManagerCreationDtoRequest {


    private String primaryBlock;

    private String role;

    public ManagerCreationDtoRequest(String primaryBlock, String role) {
        this.primaryBlock = primaryBlock;
        this.role = role;
    }

    public String getPrimaryBlock() {
        return primaryBlock;
    }

    public void setPrimaryBlock(String primaryBlock) {
        this.primaryBlock = primaryBlock;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
