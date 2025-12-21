package com.sanjeeban.WorkflowService.entity;


import jakarta.persistence.*;

@Entity
@Table(
        name = "t_resident_complaint_mapping",
        schema="facility_workorder",
        uniqueConstraints = {
                @UniqueConstraint(name="unique_entry",columnNames = {"resident_unique_id","complaint_id"})
        }
)
public class ResidentComplaintMapping {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="resident_unique_id")
    private String residentUniqueId;


    @Column(name="complaint_id")
    private Long complaintId;


    public ResidentComplaintMapping() {
    }

    public ResidentComplaintMapping(String residentUniqueId, Long complaintId) {
        this.residentUniqueId = residentUniqueId;
        this.complaintId = complaintId;
    }

    public String getResidentUniqueId() {
        return residentUniqueId;
    }

    public void setResidentUniqueId(String residentUniqueId) {
        this.residentUniqueId = residentUniqueId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }
}
