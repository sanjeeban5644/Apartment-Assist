package com.sanjeeban.WorkflowService.entity;


import com.sanjeeban.WorkflowService.enums.WorkflowStatusLegend;
import jakarta.persistence.*;

@Entity
@Table(
        name = "t_workflow_status_master",
        schema="facility_workorder"
)
public class WorkflowStatusMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "workflow_status", nullable = false)
    private WorkflowStatusLegend workflowStatus;

    @Column(name = "sub_status")
    private String subStatus;

    @Column(name = "status_code", nullable = false, unique = true)
    private Integer statusCode;


    public WorkflowStatusMaster(Long id, WorkflowStatusLegend workflowStatus, String subStatus, Integer statusCode) {
        this.id = id;
        this.workflowStatus = workflowStatus;
        this.subStatus = subStatus;
        this.statusCode = statusCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkflowStatusLegend getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(WorkflowStatusLegend workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
