package com.sanjeeban.ResidentService.controller;


import com.sanjeeban.ResidentService.dto.LodgeComplaintDto;
import com.sanjeeban.ResidentService.dto.ResidentDetailsDto;
import com.sanjeeban.ResidentService.service.ResidentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.net.URI;

@RestController
@RequestMapping(path = "/resident")
public class ResidentRequestController {

    private ResidentRequestService residentRequestService;



    @Autowired
    public ResidentRequestController(ResidentRequestService residentRequestService){
        this.residentRequestService = residentRequestService;
    }



    @GetMapping(path = "/getDetails")
    public ResidentDetailsDto getResidentDetails(@RequestParam(name="UniqueId") String uniqueId){
        ResidentDetailsDto response = new ResidentDetailsDto();

        response = residentRequestService.getResidentDetails(uniqueId);

        return response;
    }


    @PostMapping(path="/lodgeComplaint")
    public String lodgeComplaint(@RequestBody LodgeComplaintDto request){

        String response = residentRequestService.postComplaint(request);





        return null;

    }





}
