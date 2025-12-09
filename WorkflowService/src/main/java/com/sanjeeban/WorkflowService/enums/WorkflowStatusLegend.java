package com.sanjeeban.WorkflowService.enums;

public enum WorkflowStatusLegend {


        PENDING_WITH_BM("PENDING_WITH_BM"),
        PENDING_WITH_GM("PENDING_WITH_GM"),
        PENDING_WITH_GM_TECHNICIAN("PENDING_WITH_GM_TECHNICIAN"),
        PENDING_WITH_BM_TECHNICIAN("PENDING_WITH_BM_TECHNICIAN"),
        JOB_COMPLETED_TECHNICIAN("JOB_COMPLETED_TECHNICIAN"),
        JOB_COMPLETED_RESIDENT("JOB_COMPLETED_RESIDENT"),
        JOB_CLOSURE_GM("JOB_CLOSURE_GM"),
        JOB_CLOSURE_BM("JOB_CLOSURE_BM");

        private final String code;

    WorkflowStatusLegend(String code) {
            this.code = code;
        }

    public String getCode() {
            return code;
        }


}
