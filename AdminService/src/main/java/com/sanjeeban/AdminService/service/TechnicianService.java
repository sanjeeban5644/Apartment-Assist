package com.sanjeeban.AdminService.service;


import com.netflix.discovery.converters.Auto;
import com.sanjeeban.AdminService.customException.TechnicianInvalid;
import com.sanjeeban.AdminService.dto.TechnicianCreationDtoRequest;
import com.sanjeeban.AdminService.dto.TechnicianDetailsDto;
import com.sanjeeban.AdminService.entity.Technician;
import com.sanjeeban.AdminService.repository.TechnicianRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TechnicianService {

    private ModelMapper modelMapper;
    private ManagerService managerService;

    private TechnicianRepository technicianRepository;

    @Autowired
    public  TechnicianService(ModelMapper modelMapper, ManagerService managerService,TechnicianRepository technicianRepository){
        this.modelMapper = modelMapper;
        this.managerService = managerService;
        this.technicianRepository = technicianRepository;
    }




    public TechnicianDetailsDto createNewTechnician(TechnicianCreationDtoRequest request) {

        TechnicianDetailsDto response = new TechnicianDetailsDto();

        if(request.getFirstName() ==null || request.getLastName()==null || request.getFirstName().isEmpty() || request.getLastName().isEmpty()){
            throw new TechnicianInvalid("Details not sufficient to create new Technician");
        }

        if(request.getAssignedBlock()==null ||request.getAssignedBlock().isEmpty()) request.setAssignedBlock("ALL");

        Technician newTechnician = modelMapper.map(request, Technician.class);

        String id = managerService.generateNewManagerId();
        String currYear = managerService.getCurrYear();

        String technicianId = id+currYear+request.getAssignedBlock();

        newTechnician.setTechnicianId(technicianId);
        technicianRepository.save(newTechnician);

        return modelMapper.map(newTechnician,TechnicianDetailsDto.class);
    }

    public List<TechnicianDetailsDto> getTechnicianDetails(String technicianId) {

        List<Technician> listOfTechnician = new ArrayList<>();
        List<TechnicianDetailsDto> listOfTechnicianDto = new ArrayList<>();
        if(technicianId==null || technicianId.isEmpty() || technicianId.equals("")) technicianId = "ALL";

        if(technicianId.equals("ALL")){
            listOfTechnician = technicianRepository.findAll();
            for(Technician tech : listOfTechnician){
                TechnicianDetailsDto currTechnician = modelMapper.map(tech,TechnicianDetailsDto.class);
                listOfTechnicianDto.add(currTechnician);
            }
        }else{
            final String id = technicianId;
            Technician currTech  = technicianRepository.findTechnicianBytechnicianId(technicianId).orElseThrow(() -> new TechnicianInvalid("Technician not found with id : "+id));
            TechnicianDetailsDto currTechnician = modelMapper.map(currTech,TechnicianDetailsDto.class);
            listOfTechnicianDto.add(currTechnician);
        }

        return listOfTechnicianDto;
    }
}
