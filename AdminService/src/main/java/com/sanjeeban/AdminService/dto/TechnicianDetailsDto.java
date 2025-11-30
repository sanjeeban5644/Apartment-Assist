package com.sanjeeban.AdminService.dto;


public class TechnicianDetailsDto {

    private String technicianId;
    private String firstName;
    private String lastName;
    private Integer totalCompletedTasks;
    private String role;

    private String  assignedBlock;

    public TechnicianDetailsDto() {
    }

    public TechnicianDetailsDto(String technicianId, String firstName, String lastName, Integer totalCompletedTasks, String role, String assignedBlock) {
        this.technicianId = technicianId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalCompletedTasks = totalCompletedTasks;
        this.role = role;
        this.assignedBlock = assignedBlock;
    }

    public String getAssignedBlock() {
        return assignedBlock;
    }

    public void setAssignedBlock(String assignedBlock) {
        this.assignedBlock = assignedBlock;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
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

    public Integer getTotalCompletedTasks() {
        return totalCompletedTasks;
    }

    public void setTotalCompletedTasks(Integer totalCompletedTasks) {
        this.totalCompletedTasks = totalCompletedTasks;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
