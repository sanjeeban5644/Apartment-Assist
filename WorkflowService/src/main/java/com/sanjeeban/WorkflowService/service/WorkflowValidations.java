package com.sanjeeban.WorkflowService.service;


import com.sanjeeban.WorkflowService.dal.WorkflowAccessLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class WorkflowValidations {


    private WorkflowAccessLayer workflowAccessLayer;

    @Autowired
    public WorkflowValidations(WorkflowAccessLayer workflowAccessLayer){
        this.workflowAccessLayer = workflowAccessLayer;
    }



    public boolean validateUserWithRequestType(String usercode, String requestType) {
        String strValidRequests = workflowAccessLayer.validateUserRequest(usercode);

        return Arrays.stream(strValidRequests.split("#"))
                .anyMatch(code -> code.equals(requestType));
    }

    public boolean checkRequestValidity() {

        // check whether the request is valid.



        return false;
    }
}
