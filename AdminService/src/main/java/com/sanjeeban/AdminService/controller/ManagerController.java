package com.sanjeeban.AdminService.controller;


import com.sanjeeban.AdminService.dto.ManagerCreationDtoRequest;
import com.sanjeeban.AdminService.dto.ManagerDetailsDto;
import com.sanjeeban.AdminService.dto.PendingApprovalsResponseDto;
import com.sanjeeban.AdminService.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/admin")
public class ManagerController {

    private ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService){
        this.managerService = managerService;
    }

    @PostMapping(path="/createManager")
    public ResponseEntity<ManagerDetailsDto> createManager(@RequestBody ManagerCreationDtoRequest request){

        ManagerDetailsDto response = new ManagerDetailsDto();
        response = managerService.createNewManager(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/getManagers")
    public ResponseEntity<List<ManagerDetailsDto>> getManagerDetails(@RequestParam(name="ManagerId",required = false) String managerId){
        List<ManagerDetailsDto> detailList = new ArrayList<>();
        detailList = managerService.getManagerDetails(managerId);

        return ResponseEntity.ok(detailList);
    }

//    @PostMapping(path="/approveRejectWorkorder")
//    public ResponseEntity<String> approveRejectAssignWorkorder(){
//        return "";
//    }

    @GetMapping(path="/showPendingApprovals")
    public ResponseEntity<List<PendingApprovalsResponseDto>> showAllPendingApprovals(@RequestParam(name="ManagerId",required = true) String managerId){
        return ResponseEntity.ok(managerService.showPendingApprovals(managerId));
    }








}
