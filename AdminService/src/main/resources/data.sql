-- Insert Residents into facility_workorder.t_resident
INSERT INTO facility_workorder.t_resident
(block_no, floor_no, aprtmnt_no, first_name, last_name, family_number, role)
VALUES ('1', '2', '201', 'Amit', 'Sharma', '4', 'res');

INSERT INTO facility_workorder.t_resident
(block_no, floor_no, aprtmnt_no, first_name, last_name, family_number, role)
VALUES ('1', '3', '302', 'Priya', 'Das', '3', 'res');

INSERT INTO facility_workorder.t_resident
(block_no, floor_no, aprtmnt_no, first_name, last_name, family_number, role)
VALUES ('2', '1', '101', 'Rahul', 'Sen', '5', 'res');

INSERT INTO facility_workorder.t_resident
(block_no, floor_no, aprtmnt_no, first_name, last_name, family_number, role)
VALUES ('2', '4', '402', 'Sneha', 'Roy', '2', 'res');

INSERT INTO facility_workorder.t_resident
(block_no, floor_no, aprtmnt_no, first_name, last_name, family_number, role,resident_unique_id)
VALUES ('3', '2', '203', 'Arjun', 'Mukherjee', '6', 'res','49449kdflv');



-- Insert Technicians into facility_workorder.t_technician
INSERT INTO facility_workorder.t_technician (technician_id, first_name, last_name, total_completed_tasks, role)
VALUES ('TECH0001', 'Sandeep', 'Kumar', 45, 'tech');

INSERT INTO facility_workorder.t_technician (technician_id, first_name, last_name, total_completed_tasks, role)
VALUES ('TECH0002', 'Kiran', 'Patel', 30, 'tech');

INSERT INTO facility_workorder.t_technician (technician_id, first_name, last_name, total_completed_tasks, role)
VALUES ('TECH0003', 'Vikram', 'Singh', 55, 'tech');

INSERT INTO facility_workorder.t_technician (technician_id, first_name, last_name, total_completed_tasks, role)
VALUES ('TECH0004', 'Ravi', 'Mehta', 20, 'tech');

INSERT INTO facility_workorder.t_technician (technician_id, first_name, last_name, total_completed_tasks, role)
VALUES ('TECH0005', 'Anil', 'Roy', 40, 'tech');



-- Insert Managers into facility_workorder.t_manager
INSERT INTO facility_workorder.t_manager (manager_id, role, primary_block)
VALUES ('MNG0000001A', 'mng', 'Block-1');

INSERT INTO facility_workorder.t_manager (manager_id, role, primary_block)
VALUES ('MNG0000002B', 'mng', 'Block-2');

INSERT INTO facility_workorder.t_manager (manager_id, role, primary_block)
VALUES ('MNG0000003C', 'mng', 'Block-3');

INSERT INTO facility_workorder.t_manager (manager_id, role, primary_block)
VALUES ('MNG0000004D', 'mng', 'Block-1');

INSERT INTO facility_workorder.t_manager (manager_id, role, primary_block)
VALUES ('MNG0000005E', 'mng', 'Block-2');