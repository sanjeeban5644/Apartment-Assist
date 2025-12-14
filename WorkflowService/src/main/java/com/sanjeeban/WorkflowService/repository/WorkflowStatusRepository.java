package com.sanjeeban.WorkflowService.repository;

import com.sanjeeban.WorkflowService.entity.WorkflowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowStatusRepository extends JpaRepository<WorkflowStatus,Long> {


}
