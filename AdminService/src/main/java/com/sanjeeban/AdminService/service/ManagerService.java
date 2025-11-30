package com.sanjeeban.AdminService.service;


import com.sanjeeban.AdminService.customException.ManagerInvalidException;
import com.sanjeeban.AdminService.dto.ManagerCreationDtoRequest;
import com.sanjeeban.AdminService.dto.ManagerDetailsDto;
import com.sanjeeban.AdminService.entity.Manager;
import com.sanjeeban.AdminService.repository.ManagerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {

    private ManagerRepository managerRepository;

    private ModelMapper modelMapper;


    @Autowired
    public ManagerService(ManagerRepository managerRepository,ModelMapper modelMapper){
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
    }


    public ManagerDetailsDto createNewManager(ManagerCreationDtoRequest request) {

        Manager newManager = new Manager();
        if(request.getRole()==null || request.getRole().isEmpty() || request.getRole().equals("")) throw new ManagerInvalidException("Manager without role is invalid");

        if(request.getRole().equalsIgnoreCase("GM")) request.setPrimaryBlock("ALL");

        if((request.getPrimaryBlock()==null || request.getPrimaryBlock().isEmpty() || request.getPrimaryBlock().equals(""))) throw new ManagerInvalidException("Manager should be assigned a primary block");

        String managerId = generateNewManagerId();
        String additionalInfo = getCurrYear()+request.getRole()+request.getPrimaryBlock();
        String finalManagerId = managerId+additionalInfo;

        newManager.setManagerId(finalManagerId);
        newManager.setRole(request.getRole());
        newManager.setPrimaryBlock(request.getPrimaryBlock());

        managerRepository.save(newManager);

        return modelMapper.map(newManager, ManagerDetailsDto.class);
    }

    private String generateNewManagerId() {

        SecureRandom secureRandom = new SecureRandom();
        Long secureIdLong = 10000000 + secureRandom.nextLong(90000000);
        return String.valueOf(secureIdLong);
    }

    private String getCurrYear(){
        LocalDateTime currDataTime = LocalDateTime.now();
        int currYear = currDataTime.getYear();
        return String.valueOf(currYear);
    }


    public List<ManagerDetailsDto> getManagerDetails(String managerId) {

        List<ManagerDetailsDto> listOfManagers = new ArrayList<>();
        List<Manager> details = new ArrayList<>();

        if(managerId==null || managerId.isEmpty() ||managerId.equalsIgnoreCase("")){
            managerId="ALL";
        }

        if(managerId.equalsIgnoreCase("ALL")){
            // get all managers
            details = managerRepository.findAll();

            for(Manager mng : details){
                ManagerDetailsDto currManager = modelMapper.map(mng, ManagerDetailsDto.class);
                listOfManagers.add(currManager);
            }

        }else{
            final String finalManagerId = managerId;
            Manager man  =  managerRepository.findManagerBymanagerId(managerId).orElseThrow(() -> new ManagerInvalidException("Manager does not exist with id : "+ finalManagerId));
            ManagerDetailsDto currManager = modelMapper.map(man, ManagerDetailsDto.class);
            listOfManagers.add(currManager);
        }
        return listOfManagers;

    }
}
