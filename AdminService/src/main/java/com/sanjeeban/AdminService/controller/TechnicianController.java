package com.sanjeeban.AdminService.controller;


import com.sanjeeban.AdminService.dto.TechnicianCreationDtoRequest;
import com.sanjeeban.AdminService.dto.TechnicianDetailsDto;
import com.sanjeeban.AdminService.service.TechnicianService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class TechnicianController {

    private TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService){
        this.technicianService = technicianService;
    }



    @PostMapping(path="/createTechnician")
    public ResponseEntity<TechnicianDetailsDto> createTechnician(@RequestBody TechnicianCreationDtoRequest request){
        TechnicianDetailsDto response = new TechnicianDetailsDto();

        response = technicianService.createNewTechnician(request);

        return ResponseEntity.ok(response);

    }

    @GetMapping(path="/getTechnician")
    public ResponseEntity<List<TechnicianDetailsDto>> getTechnician(@RequestParam(name="technicianId", required = false)String technicianId){
        List<TechnicianDetailsDto> list = new ArrayList<>();
        list = technicianService.getTechnicianDetails(technicianId);
        return ResponseEntity.ok(list);
    }




}
