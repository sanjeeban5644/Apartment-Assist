package com.sanjeeban.AdminService.dto;

public class TechnicianCreationDtoRequest {

    private String firstName;
    private String lastName;
    private String role;

    private String assignedBlock;

    public TechnicianCreationDtoRequest(String firstName, String lastName, String role,String assignedBlock) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.assignedBlock = assignedBlock;
    }


    public String getAssignedBlock() {
        return assignedBlock;
    }

    public void setAssignedBlock(String assignedBlock) {
        this.assignedBlock = assignedBlock;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
