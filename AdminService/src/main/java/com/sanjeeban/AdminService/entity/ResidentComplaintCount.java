package com.sanjeeban.AdminService.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(
        name = "t_resident_complaint_count",
        schema="facility_workorder",
        uniqueConstraints = {
                @UniqueConstraint(name="unique_entry",columnNames = {"resident_unique_id","complaint_date"})
        }
)
public class ResidentComplaintCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="resident_unique_id",nullable = false)
    private String residentUniqueId;


    @Column(name="complaint_date",nullable = false)
    private Date complaintDate;


    @Column(name="complaint_count",nullable = false)
    private Integer complaintCount;


    public ResidentComplaintCount() {
    }

    public ResidentComplaintCount(String residentUniqueId, Date complaintDate, Integer complaintCount) {
        this.residentUniqueId = residentUniqueId;
        this.complaintDate = complaintDate;
        this.complaintCount = complaintCount;
    }


    public String getResidentUniqueId() {
        return residentUniqueId;
    }

    public void setResidentUniqueId(String residentUniqueId) {
        this.residentUniqueId = residentUniqueId;
    }

    public Date getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
    }

    public Integer getComplaintCount() {
        return complaintCount;
    }

    public void setComplaintCount(Integer complaintCount) {
        this.complaintCount = complaintCount;
    }
}
