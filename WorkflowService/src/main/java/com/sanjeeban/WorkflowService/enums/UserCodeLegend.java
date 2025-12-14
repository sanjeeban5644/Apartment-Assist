package com.sanjeeban.WorkflowService.enums;

public enum UserCodeLegend {

    RESIDENT("res"),
    ADMIN("admin"),
    TECHNICIAN("tech");


    private String code;

    UserCodeLegend(String res) {
    }

    public String getCode() {
        return code;
    }

}
