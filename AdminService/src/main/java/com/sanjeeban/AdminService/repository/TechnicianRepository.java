package com.sanjeeban.AdminService.repository;

import com.sanjeeban.AdminService.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnicianRepository extends JpaRepository<Technician,String> {



    //public Optional<Manager> findManagerBymanagerId(String managerId);

    public Optional<Technician> findTechnicianBytechnicianId(String technicianId);
}
