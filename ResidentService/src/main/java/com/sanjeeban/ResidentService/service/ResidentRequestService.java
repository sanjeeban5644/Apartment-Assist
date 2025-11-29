package com.sanjeeban.ResidentService.service;


import com.sanjeeban.ResidentService.dto.ResidentDetailsDto;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Component
public class ResidentRequestService {


    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public ResidentRequestService(DiscoveryClient discoveryClient, RestClient restClient) {
        this.discoveryClient = discoveryClient;
        this.restClient = restClient;
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
}
