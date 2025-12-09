INSERT INTO facility_workorder.t_workflow_status_master
    (workflow_status, sub_status, status_code)
VALUES
    ('PENDING_WITH_BM', NULL, 100),
    ('PENDING_WITH_GM', NULL, 200),
    ('PENDING_WITH_GM_TECHNICIAN', NULL, 300),
    ('PENDING_WITH_BM_TECHNICIAN', NULL, 400),
    ('JOB_COMPLETED_TECHNICIAN', NULL, 500),
    ('JOB_COMPLETED_RESIDENT', NULL, 600),
    ('JOB_CLOSURE_GM', NULL, 700),
    ('JOB_CLOSURE_BM', NULL, 800);
