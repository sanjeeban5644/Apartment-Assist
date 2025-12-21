package com.sanjeeban.ResidentService.service;


import com.netflix.discovery.converters.Auto;
import com.sanjeeban.ResidentService.customException.RequestLimitReached;
import com.sanjeeban.ResidentService.dal.ResidentDataAccessLayer;
import com.sanjeeban.ResidentService.dto.LodgeComplaintDto;
import com.sanjeeban.ResidentService.dto.ResidentDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ResidentRequestService {


    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    private ResidentDataAccessLayer residentDataAccessLayer;

    @Autowired
    public ResidentRequestService(DiscoveryClient discoveryClient, RestClient restClient,ResidentDataAccessLayer residentDataAccessLayer) {
        this.discoveryClient = discoveryClient;
        this.restClient = restClient;
        this.residentDataAccessLayer = residentDataAccessLayer;
    }

    public ResidentDetailsDto getResidentDetails(String uniqueId) {

        ResidentDetailsDto response = new ResidentDetailsDto();

        ServiceInstance adminService = discoveryClient.getInstances("AdminService").get(0);
        URI uri = URI.create(adminService.getUri().toString()+"/admin/getResidentDetailsByUniqueId?ResidentUniqueId="+uniqueId);
        System.out.println("Admin -> Admin url +is : "+uri);
        try{
            response = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(ResidentDetailsDto.class);
        }catch(Exception e){
            e.printStackTrace();
        }

        return response;
    }

    public String postComplaint(LodgeComplaintDto request) {

        String response = null;

        String complaintId = generateComplaintId(request.getResidentUniqueId());


        request.setComplaintId(complaintId);

        ServiceInstance adminService = discoveryClient.getInstances("WorkflowService").get(0);
        URI uri = URI.create(adminService.getUri().toString()+"/workflow/initiateWorkflow");
        System.out.println("Workflow -> Workflow url +is : "+uri);
        try{
            response = restClient.post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(String.class);
        }catch(Exception e){
            e.printStackTrace();
        }

        return response;

    }

    private String generateComplaintId(String residentUniqueId) {

        String detailsFromResidentUniqueId = residentUniqueId.substring(8,14);
        String currDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String formattedDate = currDate.substring(0,4)+currDate.substring(6,8);
        //String reversedDate = String.valueOf(new StringBuilder(formattedDate).reverse());
        // check count

        int count = residentDataAccessLayer.fetchCurrentCount(residentUniqueId);
        if(count==-1){
            // handle the case where many requests have been made.
            throw new RequestLimitReached("Request Limit reached for Resident -> "+residentUniqueId);
        }
        return formattedDate+detailsFromResidentUniqueId+String.valueOf(count);
    }
}
