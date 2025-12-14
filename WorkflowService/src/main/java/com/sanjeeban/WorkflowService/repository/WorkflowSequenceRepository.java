package com.sanjeeban.WorkflowService.repository;

import com.sanjeeban.WorkflowService.entity.WorkflowSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;


public interface WorkflowSequenceRepository extends JpaRepository<WorkflowSequence, LocalDate> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select w from WorkflowSequence w where w.date = :date")
    Optional<WorkflowSequence> findByDateForUpdate(LocalDate date);



}
