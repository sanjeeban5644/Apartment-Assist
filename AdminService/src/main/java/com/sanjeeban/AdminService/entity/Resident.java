package com.sanjeeban.AdminService.entity;


import jakarta.persistence.*;

@Entity
@Table(
        name = "t_resident",
        schema="facility_workorder"
)
public class Resident {

    @EmbeddedId
    private ResidentKey residentId;

    @Column(name="first_name",length=100)
    private String firstName;

    @Column(name="last_name",length=100)
    private String lastName;

    @Column(name="family_number")
    private String familyNumber;

    @Column
    private String role;

    @Column(name="resident_unique_id",nullable = true)
    private String residentUniqueId;

    public ResidentKey getResidentId() {
        return residentId;
    }

    public void setResidentId(ResidentKey residentId) {
        this.residentId = residentId;
    }

    public String getResidentUniqueId() {
        return residentUniqueId;
    }

    public void setResidentUniqueId(String residentUniqueId) {
        this.residentUniqueId = residentUniqueId;
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
}
