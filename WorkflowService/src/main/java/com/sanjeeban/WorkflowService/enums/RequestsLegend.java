package com.sanjeeban.WorkflowService.enums;

public enum RequestsLegend {

    // Resident Requests
    NO_WATER("NWAT"),
    NO_POWER("NPWR"),
    GAS_LEAK("GLEK"),
    LIFT_NOT_WORKING("LIFT"),
    PARKING_BLOCKED("PBLK"),
    NO_INTERNET("NINT"),
    NO_CLEANING("NCLN"),
    NO_SECURITY("NSCR"),
    NO_WASTE_COLLECTION("NWST"),
    NO_TV_SIGNAL("NTVS"),

    // Admin Requests
    FIRE_DRILL("FDRL"),            // Organize fire safety drill
    AUDIT_COMPLIANCE("AUDT"),      // Compliance/audit request
    POLICY_UPDATE("PLCY"),         // Update housing policy
    STAFF_ALLOCATION("STAF"),      // Assign staff to duties
    BUDGET_APPROVAL("BUDG"),       // Approve facility budget

    // Technician Requests
    PLUMBING_FIX("PLMB"),          // Plumbing repair
    ELECTRICAL_FIX("ELEC"),        // Electrical repair
    HVAC_MAINTENANCE("HVAC"),      // Heating/ventilation/AC maintenance
    CCTV_REPAIR("CCTV"),           // Security camera repair
    GENERATOR_SERVICE("GENS");     // Generator servicing

    private final String code;

    RequestsLegend(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}