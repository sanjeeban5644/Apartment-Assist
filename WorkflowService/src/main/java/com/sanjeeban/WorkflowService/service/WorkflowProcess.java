package com.sanjeeban.WorkflowService.service;

import com.sanjeeban.WorkflowService.customException.DuplicateWorkflowException;
import com.sanjeeban.WorkflowService.dal.WorkflowAccessLayer;
import com.sanjeeban.WorkflowService.dto.WorkflowRequestDto;

import com.sanjeeban.WorkflowService.entity.*;
import com.sanjeeban.WorkflowService.repository.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WorkflowProcess {

    private WorkflowValidations workflowValidations;
    private WorkflowAccessLayer workflowAccessLayer;
    private WorkflowSequenceRepository workflowSequenceRepository;

    private WorkflowDiaryRepository workflowDiaryRepository;

    private WorkflowStatusRepository workflowStatusRepository;

    private WorkflowStatusMasterRepository workflowStatusMasterRepository;

    private ResidentComplaintMappingRepository residentComplaintMappingRepository;



    @Autowired
    public WorkflowProcess(WorkflowValidations workflowValidations,WorkflowSequenceRepository workflowSequenceRepository,WorkflowAccessLayer workflowAccessLayer,WorkflowDiaryRepository workflowDiaryRepository,WorkflowStatusRepository workflowStatusRepository,WorkflowStatusMasterRepository workflowStatusMasterRepository,ResidentComplaintMappingRepository residentComplaintMappingRepository){
        this.workflowValidations = workflowValidations;
        this.workflowSequenceRepository = workflowSequenceRepository;
        this.workflowAccessLayer = workflowAccessLayer;
        this.workflowDiaryRepository = workflowDiaryRepository;
        this.workflowStatusRepository = workflowStatusRepository;
        this.workflowStatusMasterRepository = workflowStatusMasterRepository;
        this.residentComplaintMappingRepository = residentComplaintMappingRepository;
    }


    public String triggerWorkflow(WorkflowRequestDto request) {

        // generating a unique workflow id
        Long workflowId = Long.valueOf(workflowAccessLayer.generateWorkflowId());

        //saving the master table || resident -> complaint_id

        ResidentComplaintMapping residentComplaintMappingObject = new ResidentComplaintMapping();
        residentComplaintMappingObject.setComplaintId(request.getComplaintId());
        residentComplaintMappingObject.setResidentUniqueId(request.getResidentUniqueId());
        residentComplaintMappingRepository.save(residentComplaintMappingObject);


        JSONObject json = new JSONObject();
        String response = "";
        String usercode = request.getUserCode();
        String requestType = request.getRequestType();

        // validating that the user requests the correct request-type.
        boolean isValidRequestType = workflowValidations.validateUserWithRequestType(usercode,requestType);
        //boolean isValidUser = workflowValidations.checkValidUser(usercode)

        if(!isValidRequestType){

            String currStatus = saveStatus("IR",workflowId,request.getComplaintId(),request.getUserCode());

            json.put("complaintId",request.getComplaintId());
            json.put("responseCode","400");
            json.put("responseMsg","Invalid user and reqeust Mapping");
            json.put("currStatus","ip");
            return json.toString();
        }else{
            // everything is normal. going to next stage




        }
        return "--in progress--";
    }


    private String saveStatus(String status, Long workflowId, Long complaintId,String userCode) {

        // first check if workflow id exists in workflow diary
        int count = workflowAccessLayer.checkIfWorkflowExists(workflowId);
        if(count==0){
            // this is a new workflow
            // saving in workflow diary

            WorkflowDiary wfkDiary = new WorkflowDiary();
            wfkDiary.setWorkflowStatusId(workflowId);
            wfkDiary.setStatus(status);
            wfkDiary.setSubStatus(String.valueOf(workflowStatusMasterRepository.findStatusCodeFromStatus(status).orElse(250)));
            wfkDiary.setUpdatedAt(LocalDateTime.now());
            wfkDiary.setUpdatedBy(userCode);
            wfkDiary.setRemarks("first entry");
            workflowDiaryRepository.save(wfkDiary);

            //saving in workflow status

            WorkflowStatus wfkStatus = new WorkflowStatus();
            wfkStatus.setComplaintId(complaintId);
            wfkStatus.setWorkflowStatusId(workflowId);
            wfkStatus.setCurrentStatus(status);
            wfkStatus.setSubstatus(String.valueOf(workflowStatusMasterRepository.findStatusCodeFromStatus(status).orElse(250)));
            wfkStatus.setUpdatedAt(LocalDateTime.now());
            wfkStatus.setUpdatedBy(userCode);

            workflowStatusRepository.save(wfkStatus);
        }else{
            throw  new DuplicateWorkflowException("Workflow Id : "+workflowId);
        }

        return status;
    }


}
