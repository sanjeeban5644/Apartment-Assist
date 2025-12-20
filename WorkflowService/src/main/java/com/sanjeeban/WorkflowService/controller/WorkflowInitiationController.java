package com.sanjeeban.WorkflowService.controller;


import com.sanjeeban.WorkflowService.dto.WorkflowRequestDto;
import com.sanjeeban.WorkflowService.service.WorkflowProcess;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/workflow")
@RestController
public class WorkflowInitiationController {

    private WorkflowProcess workflowProcess;



    @Autowired
    public WorkflowInitiationController(WorkflowProcess workflowProcess){
        this.workflowProcess = workflowProcess;
    }


    @Operation(summary = "Initiate Workflow Process",description = "Initiate the workflow process for the complaint id")
    @PostMapping(path="/initiateWorkflow")
    public ResponseEntity<String> initiateWorkflow(@RequestBody WorkflowRequestDto request){
        String response = "";

        response = workflowProcess.triggerWorkflow(request);

        //response.append(request.getComplaintId().toString());




        return ResponseEntity.ok(String.valueOf(response));
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