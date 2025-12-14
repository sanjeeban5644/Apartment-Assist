package com.sanjeeban.WorkflowService.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_request_master", schema = "facility_workorder")
public class RequestMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_name", nullable = false, length = 100)
    private String requestName;

    @Column(name = "request_code", nullable = false, length = 10)
    private String requestCode;

    public RequestMaster() {}

    public RequestMaster(String requestName, String requestCode) {
        this.requestName = requestName;
        this.requestCode = requestCode;
    }

    public Long getId() {
        return id;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }
}