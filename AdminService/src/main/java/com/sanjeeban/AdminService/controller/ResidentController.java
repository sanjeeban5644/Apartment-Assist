package com.sanjeeban.AdminService.controller;


import com.sanjeeban.AdminService.dto.ResidentCreationUpdationDto;
import com.sanjeeban.AdminService.dto.ResidentCreationUpdationRequest;
import com.sanjeeban.AdminService.dto.ResidentDetailsDto;
import com.sanjeeban.AdminService.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class ResidentController {

    private ResidentService residentService;

    @Autowired
    public ResidentController(ResidentService residentService){
        this.residentService = residentService;
    }


    @PostMapping(path="/createOrUpdateResident")
    private ResponseEntity<ResidentDetailsDto> createOrUpdateResident(@RequestBody ResidentCreationUpdationRequest request){

        ResidentDetailsDto response = new ResidentDetailsDto();

        // check if resident already exists.
        response = residentService.createOrUpdateResident(request);

        return ResponseEntity.ok(response);
    }


    @GetMapping(path="/getResidentDetailsByUniqueId")
    private ResponseEntity<ResidentDetailsDto> getResidentDetails(@RequestParam(name="ResidentUniqueId")String residentUniqueId){
        ResidentDetailsDto response = new ResidentDetailsDto();
        response = residentService.getResidentDetails(residentUniqueId);
        return ResponseEntity.ok(response);
    }







}
