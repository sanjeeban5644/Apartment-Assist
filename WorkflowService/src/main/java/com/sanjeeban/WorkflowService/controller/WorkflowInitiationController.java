package com.sanjeeban.WorkflowService.controller;


import com.sanjeeban.WorkflowService.dto.InitiateWorkflowDetailsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/workflow")
@RestController
public class WorkflowInitiationController {


    @Operation(summary = "Initiate Workflow Process",description = "Initiate the workflow process for the complaint")
    @PostMapping(path="/initiateWorkflow")
    public ResponseEntity<String> initiateWorkflow(InitiateWorkflowDetailsDto request){
        String response = null;




        return ResponseEntity.ok(response);
    }


}


//
//@Operation(summary = "Download Resident PDF", description = "Generates and downloads resident details as a PDF")
//@ApiResponse(responseCode = "200", description = "PDF generated successfully", content = @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary")))
//@GetMapping(path="/getResidentDetails")


//    @Id
//    @Column(name="workflow_status_id")
//    private Long workflowStatusId;
//
//    @Column(name = "complaint_id", nullable = false)
//    private Long complaintId;
//
//    @Column(name="current_status",nullable = false)
//    private String currentStatus;
//
//    @Column(name = "updated_by", nullable = false)
//    private String updatedBy;
//
//    @Column(name = "remarks")
//    private String remarks;
//
//    @Column(name = "updated_at", nullable = false)
//    private LocalDateTime updatedAt = LocalDateTime.now();