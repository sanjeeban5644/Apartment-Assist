package com.sanjeeban.WorkflowService.repository;

import com.sanjeeban.WorkflowService.entity.WorkflowSequence;
import com.sanjeeban.WorkflowService.entity.WorkflowStatusMaster;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkflowStatusMasterRepository extends JpaRepository<WorkflowStatusMaster,Long> {

    @Query("select t.statusCode from WorkflowStatusMaster t where t.subStatus = :status")
    Optional<Integer> findStatusCodeFromStatus(String status);


}
