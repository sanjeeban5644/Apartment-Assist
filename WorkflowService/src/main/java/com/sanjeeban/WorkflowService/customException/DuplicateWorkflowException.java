package com.sanjeeban.WorkflowService.customException;

public class DuplicateWorkflowException extends RuntimeException{
    public DuplicateWorkflowException(String msg){
        super(msg);
    }

}
