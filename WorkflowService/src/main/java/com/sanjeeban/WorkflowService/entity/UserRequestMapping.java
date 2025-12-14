package com.sanjeeban.WorkflowService.entity;


import com.sanjeeban.WorkflowService.enums.RequestsLegend;
import com.sanjeeban.WorkflowService.enums.UserCodeLegend;
import jakarta.persistence.*;

@Entity
@Table(
        name = "t_user_request_mapping",
        schema = "facility_workorder"
)
public class UserRequestMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_code", nullable = false, unique = true)
    private UserCodeLegend userCode;


    @Column(name = "request_codes", nullable = false, length = 10000)
    private String requestCode;

    public UserRequestMapping() {
    }

    public UserRequestMapping(Long id, UserCodeLegend userCode, String requestCode) {
        this.id = id;
        this.userCode = userCode;
        this.requestCode = requestCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCodeLegend getUserCode() {
        return userCode;
    }

    public void setUserCode(UserCodeLegend userCode) {
        this.userCode = userCode;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }
}
