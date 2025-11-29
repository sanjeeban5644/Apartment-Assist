package com.sanjeeban.AdminService.service;


import com.sanjeeban.AdminService.customException.ResidentNotFound;
import com.sanjeeban.AdminService.dto.ResidentCreationUpdationRequest;
import com.sanjeeban.AdminService.dto.ResidentDetailsDto;
import com.sanjeeban.AdminService.entity.Resident;
import com.sanjeeban.AdminService.entity.ResidentKey;
import com.sanjeeban.AdminService.repository.ResidentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ResidentService {

    private ResidentRepository residentRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ResidentService(ResidentRepository residentRepository,ModelMapper modelMapper){
        this.residentRepository = residentRepository;
        this.modelMapper = modelMapper;
    }


    public ResidentDetailsDto createOrUpdateResident(ResidentCreationUpdationRequest request) {

        ResidentDetailsDto response = new ResidentDetailsDto();

        // check if the resident already exists

        String uniqueId = request.getResidentUniqueId();

        // if unique id is present search whether the unique id is valid or not.

        boolean isUniqueIsValid = checkUniqueId(uniqueId);
        // if present then autopopulate the rest of the details. and the add new button disappears and the update button comes into action.
        if(isUniqueIsValid){
            Resident res = new Resident();
            res = residentRepository.findByresidentUniqueId(uniqueId).orElseThrow(() -> new ResidentNotFound("Resident not found with unique id : "+uniqueId));
            response =  modelMapper.map(res, ResidentDetailsDto.class);
            response.setBlockNumber(res.getResidentId().getBlockNumber());
            response.setFloorNumber(res.getResidentId().getFloorNumber());
            response.setApartmentNumber(res.getResidentId().getApartmentNumber());
            response.setStatus("Resident Exists");
            response.setStatusCode("06");
            return response;
        }

        if(uniqueId!=null && !uniqueId.isEmpty() && !isUniqueIsValid){
            response.setStatus("Unique Id does not match");
            response.setStatusCode("09");
            return response;
        }
        // unique id is not present. so creating new resident.

        Optional<Resident> existingResident = residentRepository.findByResidentIdBlockNumberAndResidentIdFloorNumberAndResidentIdApartmentNumber(request.getBlockNumber(), request.getFloorNumber(), request.getApartmentNumber());
        if(existingResident.isPresent()){
            // resident exists. go to updating it.
            response.setStatus("Resident exists without unique Id");
            response.setStatusCode("05");
            return response;
        }else{
            // goind to create a new resident.
            String newuniqueId = createNewResident(request);
            if(newuniqueId!=null && !newuniqueId.isEmpty()){
                // new resident is created.
                response.setStatus("Resident is Created with Unique Id : "+newuniqueId);
                response.setStatusCode("02");
            }
        }



        return response;

    }

    private boolean checkUniqueId(String uniqueId) {
        if(uniqueId==null || uniqueId.equals("")) return false;
        Optional<Resident> resident = residentRepository.findByresidentUniqueId(uniqueId);
        if(resident.isPresent()){
            return true;
        }
        return false;
    }

    public String createNewResident(ResidentCreationUpdationRequest request){
        Resident newResident = new Resident();
        modelMapper.map(request, newResident);
        String blockNo = request.getBlockNumber();
        String floorNo = request.getFloorNumber();
        String apartmentNo = request.getApartmentNumber();
        LocalDateTime ldt = LocalDateTime.now();
        LocalDate currDate = ldt.toLocalDate();
        String formattedDate = currDate.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String uniqueId = formattedDate+blockNo+floorNo+apartmentNo;
        newResident.setResidentUniqueId(uniqueId);

        ResidentKey residentKey = new ResidentKey();
        residentKey.setBlockNumber(blockNo);
        residentKey.setFloorNumber(floorNo);
        residentKey.setApartmentNumber(apartmentNo);

        newResident.setResidentId(residentKey);

        residentRepository.save(newResident);
        return uniqueId;
    }



    public ResidentDetailsDto getResidentDetails(String residentUniqueId) {
        ResidentDetailsDto response = new ResidentDetailsDto();
        Resident resident = new Resident();
        resident = residentRepository.findByresidentUniqueId(residentUniqueId).orElseThrow(() -> new ResidentNotFound("Resident Not Found with Resident unique Id : "+residentUniqueId));
        response =  modelMapper.map(resident,ResidentDetailsDto.class);
        response.setStatus("Found Resident successfully");
        response.setStatusCode("10");
        response.setBlockNumber(resident.getResidentId().getBlockNumber());
        response.setFloorNumber(resident.getResidentId().getFloorNumber());
        response.setApartmentNumber(resident.getResidentId().getApartmentNumber());
        return response;
    }
}
