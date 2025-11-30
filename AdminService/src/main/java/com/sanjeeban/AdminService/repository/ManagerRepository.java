package com.sanjeeban.AdminService.repository;

import com.sanjeeban.AdminService.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,String> {

    public Optional<Manager> findManagerBymanagerId(String managerId);

}
