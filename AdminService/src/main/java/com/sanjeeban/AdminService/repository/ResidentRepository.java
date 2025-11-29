package com.sanjeeban.AdminService.repository;

import com.sanjeeban.AdminService.entity.Resident;
import com.sanjeeban.AdminService.entity.ResidentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, ResidentKey> {



    public Optional<Resident> findByResidentIdBlockNumberAndResidentIdFloorNumberAndResidentIdApartmentNumber(
            String blockNumber,
            String floorNumber,
            String apartmentNumber
    );


    public Optional<Resident> findByresidentUniqueId (String residentUniqueId);
}
