package com.sanjeeban.WorkflowService.enums;

public enum WorkflowStatusLegend {



        PENDING_WITH_BM("PBM"),
        PENDING_WITH_GM("PGM"),
        PENDING_WITH_GM_TECHNICIAN("PGMT"),
        PENDING_WITH_BM_TECHNICIAN("PBMT"),
        JOB_COMPLETED_TECHNICIAN("JCT"),
        JOB_COMPLETED_RESIDENT("JCR"),
        JOB_CLOSURE_GM("JCGM"),
        JOB_CLOSURE_BM("JCBM"),
        INVALID_REQUEST("IR");

        private final String code;

        WorkflowStatusLegend(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }



}
