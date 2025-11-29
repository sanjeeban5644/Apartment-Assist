package com.sanjeeban.AdminService.dto;



public class ResidentCreationUpdationRequest {

    private String blockNumber;
    private String floorNumber;
    private String apartmentNumber;
    private String firstName;
    private String lastName;
    private String familyNumber;
    private String role;


    private String residentUniqueId;

    public ResidentCreationUpdationRequest(String blockNumber, String floorNumber, String apartmentNumber, String firstName, String lastName, String familyNumber, String role, String residentUniqueId) {
        this.blockNumber = blockNumber;
        this.floorNumber = floorNumber;
        this.apartmentNumber = apartmentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.familyNumber = familyNumber;
        this.role = role;
        this.residentUniqueId = residentUniqueId;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
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

    public String getFamilyNumber() {
        return familyNumber;
    }

    public void setFamilyNumber(String familyNumber) {
        this.familyNumber = familyNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResidentUniqueId() {
        return residentUniqueId;
    }

    public void setResidentUniqueId(String residentUniqueId) {
        this.residentUniqueId = residentUniqueId;
    }

}
