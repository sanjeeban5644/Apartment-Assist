package com.sanjeeban.AdminService.controller;


import com.sanjeeban.AdminService.dto.ResidentCreationUpdationDto;
import com.sanjeeban.AdminService.dto.ResidentCreationUpdationRequest;
import com.sanjeeban.AdminService.dto.ResidentDetailsDto;
import com.sanjeeban.AdminService.entity.Resident;
import com.sanjeeban.AdminService.helper.ExcelHelper;
import com.sanjeeban.AdminService.service.ResidentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
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


    @GetMapping(path="/getAllResidents")
    private ResponseEntity<List<Resident>> getAllResidentDetails(){
        List<Resident> listOfResidents = new ArrayList<>();

        listOfResidents = residentService.getAllResidents();

        return ResponseEntity.ok(listOfResidents);
    }



    @PostMapping("/uploadData")
    public ResponseEntity<?> uploadData(@RequestParam("file")MultipartFile file){
        if(ExcelHelper.checkExcelFormat(file)){
            this.residentService.saveExcelData(file);
            return ResponseEntity.ok(Map.of("message","File is uploaded"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload valid file");
    }



}
