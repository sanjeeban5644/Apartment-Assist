INSERT INTO facility_workorder.t_workflow_status_master
    (workflow_status, sub_status, status_code)
VALUES
    ('PENDING_WITH_BM', 'PBM', 100),
    ('PENDING_WITH_GM', 'PGM', 200),
    ('PENDING_WITH_GM_TECHNICIAN', 'PGMT', 300),
    ('PENDING_WITH_BM_TECHNICIAN', 'PBMT', 400),
    ('JOB_COMPLETED_TECHNICIAN', 'JCT', 500),
    ('JOB_COMPLETED_RESIDENT', 'JCR', 600),
    ('JOB_CLOSURE_GM', 'JCGM', 700),
    ('JOB_CLOSURE_BM', 'JCBM', 800),
    ('INVALID_REQUEST', 'IR', 900);



-- Row for RESIDENT
INSERT INTO facility_workorder.t_user_request_mapping (user_code, request_codes)
VALUES ('RESIDENT', 'NWAT#NPWR#GLEK#LIFT#PBLK#NINT#NCLN#NSCR#NWST#NTVS');

-- Row for ADMIN
INSERT INTO facility_workorder.t_user_request_mapping (user_code, request_codes)
VALUES ('ADMIN', 'FDRL#AUDT#PLCY#STAF#BUDG');

-- Row for TECHNICIAN
INSERT INTO facility_workorder.t_user_request_mapping (user_code, request_codes)
VALUES ('TECHNICIAN', 'PLMB#ELEC#HVAC#CCTV#GENS');





-- Resident Requests
INSERT INTO facility_workorder.t_request_master (request_name, request_code) VALUES
('NO_WATER', 'NWAT'),
('NO_POWER', 'NPWR'),
('GAS_LEAK', 'GLEK'),
('LIFT_NOT_WORKING', 'LIFT'),
('PARKING_BLOCKED', 'PBLK'),
('NO_INTERNET', 'NINT'),
('NO_CLEANING', 'NCLN'),
('NO_SECURITY', 'NSCR'),
('NO_WASTE_COLLECTION', 'NWST'),
('NO_TV_SIGNAL', 'NTVS');

-- Admin Requests
INSERT INTO facility_workorder.t_request_master (request_name, request_code) VALUES
('FIRE_DRILL', 'FDRL'),
('AUDIT_COMPLIANCE', 'AUDT'),
('POLICY_UPDATE', 'PLCY'),
('STAFF_ALLOCATION', 'STAF'),
('BUDGET_APPROVAL', 'BUDG');

-- Technician Requests
INSERT INTO facility_workorder.t_request_master (request_name, request_code) VALUES
('PLUMBING_FIX', 'PLMB'),
('ELECTRICAL_FIX', 'ELEC'),
('HVAC_MAINTENANCE', 'HVAC'),
('CCTV_REPAIR', 'CCTV'),
('GENERATOR_SERVICE', 'GENS');