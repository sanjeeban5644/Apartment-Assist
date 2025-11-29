package com.sanjeeban.AdminService.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "t_manager",
        schema="facility_workorder"
)
public class Manager {

    @Id
    @Column(name="manager_id")
    private Long managerId;

    @Column
    private String role;

    @Column(name="primary_block")
    private String primaryBlock;

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
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
