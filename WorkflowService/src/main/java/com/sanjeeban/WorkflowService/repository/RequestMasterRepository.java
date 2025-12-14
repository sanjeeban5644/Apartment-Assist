package com.sanjeeban.WorkflowService.repository;

import com.sanjeeban.WorkflowService.entity.RequestMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestMasterRepository extends JpaRepository<RequestMaster,Long> {

    @Query("SELECT COUNT(r) FROM RequestMaster r WHERE r.requestCode = :requestCode")
    long getCountByRequestCode(String requestCode);


}
